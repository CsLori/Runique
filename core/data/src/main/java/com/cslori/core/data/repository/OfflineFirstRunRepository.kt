package com.cslori.core.data.repository

import com.cslori.core.database.dao.RunPendingSyncDao
import com.cslori.core.database.mappers.toRun
import com.cslori.core.domain.SessionStorage
import com.cslori.core.domain.run.LocalRunDataSource
import com.cslori.core.domain.run.RemoteRunDataSource
import com.cslori.core.domain.run.Run
import com.cslori.core.domain.run.RunId
import com.cslori.core.domain.run.RunRepository
import com.cslori.core.domain.run.SyncRunScheduler
import com.cslori.core.domain.util.DataError
import com.cslori.core.domain.util.EmptyResult
import com.cslori.core.domain.util.asEmptyResult
import kotlinx.coroutines.flow.Flow
import com.cslori.core.domain.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OfflineFirstRunRepository(
    private val localRunDataSource: LocalRunDataSource,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val applicationScope: CoroutineScope,
    private val runPendingSyncDao: RunPendingSyncDao,
    private val sessionStorage: SessionStorage,
    private val syncRunScheduler: SyncRunScheduler
) : RunRepository {
    override fun getRuns(): Flow<List<Run>> {
        return localRunDataSource.getRuns()
    }

    override suspend fun fetchRuns(): EmptyResult<DataError> {
        return when (val result = remoteRunDataSource.getRuns()) {
            is Result.Error -> result.asEmptyResult()

            is Result.Success -> {
                applicationScope.launch {
                    localRunDataSource.upsertRuns(result.data)
                }
                return Result.Success(Unit)
            }
        }
    }

    override suspend fun upsertRun(
        run: Run,
        mapPicture: ByteArray
    ): EmptyResult<DataError> {
        val localResult = localRunDataSource.upsertRun(run)
        if (localResult !is Result.Success) {
            return localResult.asEmptyResult()
        }
        val runWithId = run.copy(id = localResult.data)
        val remoteResult = remoteRunDataSource.postRun(runWithId, mapPicture)

        return when (remoteResult) {
            is Result.Error -> {
                applicationScope.launch {
                    syncRunScheduler.scheduleSync(
                        type = SyncRunScheduler.SyncType.CreateRun(
                            run = runWithId,
                            mapPictureBytes = mapPicture
                        )
                    )
                }.join()
                return Result.Success(Unit)
            }

            is Result.Success -> {
                applicationScope.async {
                    localRunDataSource.upsertRun(remoteResult.data).asEmptyResult()
                }.await()
            }
        }
    }

    override suspend fun deleteRun(id: RunId) {
        localRunDataSource.deleteRun(id)

        //Edge case where the run is created offline,
        //and then deleted in the offline mode as well. In this case
        //we don't need to sync anything.
        val isPendingSync = runPendingSyncDao.getRunPendingSyncEntity(id) != null
        if (isPendingSync) {
            runPendingSyncDao.deleteRunPendingSyncEntity(id)
            return
        }

        val remoteResult = applicationScope.async {
            remoteRunDataSource.deleteRun(id)
        }.await()

        if (remoteResult is Result.Error) {
            applicationScope.launch {
                syncRunScheduler.scheduleSync(
                    type = SyncRunScheduler.SyncType.DeleteRun(id)
                )
            }.join()
        }
    }

    override suspend fun syncPendingRuns() {
        withContext(Dispatchers.IO) {
            val userId = sessionStorage.get()?.userId ?: return@withContext

            val createdRuns = async {
                runPendingSyncDao.getAllRunPendingSyncEntities(userId)
            }

            val deletedRuns = async {
                runPendingSyncDao.getAllDeletedRunPendingSyncEntities(userId)
            }

            val createJobs = createdRuns.await().map {
                launch {
                    val run = it.run.toRun()
                    when (val result = remoteRunDataSource.postRun(run, it.mapPictureBytes)) {
                        is Result.Error -> Unit
                        is Result.Success -> {
                            applicationScope.launch {
                                runPendingSyncDao.deleteRunPendingSyncEntity(it.runId)
                            }.join()
                        }
                    }
                }
            }

            val deleteJobs = deletedRuns.await().map {
                launch {
                    when (val result = remoteRunDataSource.deleteRun(it.runId)) {
                        is Result.Error -> Unit
                        is Result.Success -> {
                            applicationScope.launch {
                                runPendingSyncDao.deleteDeletedRunSyncEntity(it.runId)
                            }.join()
                        }
                    }
                }
            }

            createJobs.forEach { it.join() }
            deleteJobs.forEach { it.join() }
        }
    }
}