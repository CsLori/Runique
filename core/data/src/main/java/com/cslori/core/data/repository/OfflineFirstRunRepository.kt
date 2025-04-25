package com.cslori.core.data.repository

import com.cslori.core.domain.run.LocalRunDataSource
import com.cslori.core.domain.run.RemoteRunDataSource
import com.cslori.core.domain.run.Run
import com.cslori.core.domain.run.RunId
import com.cslori.core.domain.run.RunRepository
import com.cslori.core.domain.util.DataError
import com.cslori.core.domain.util.EmptyResult
import com.cslori.core.domain.util.asEmptyResult
import kotlinx.coroutines.flow.Flow
import com.cslori.core.domain.util.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class OfflineFirstRunRepository(
    private val localRunDataSource: LocalRunDataSource,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val applicationScope: CoroutineScope
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

        val remoteResult = applicationScope.async {
            remoteRunDataSource.deleteRun(id)
        }.await()
    }
}