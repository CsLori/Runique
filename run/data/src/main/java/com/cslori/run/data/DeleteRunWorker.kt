package com.cslori.run.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cslori.core.database.dao.RunPendingSyncDao
import com.cslori.core.domain.run.RemoteRunDataSource
import com.cslori.core.domain.run.RunRepository

class DeleteRunWorker(
    context: Context,
    private val params: WorkerParameters,
    private val remoteRunDataSource: RemoteRunDataSource,
    private val pendingSyncDao: RunPendingSyncDao
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if (runAttemptCount >= 5) {
            return Result.failure()
        }
        val runId = params.inputData.getString(RUN_ID) ?: return Result.failure()
        return when(val result = remoteRunDataSource.deleteRun(runId)) {
            is com.cslori.core.domain.util.Result.Success -> {
                pendingSyncDao.deleteDeletedRunSyncEntity(runId)
                Result.success()
            }
            is com.cslori.core.domain.util.Result.Error -> {
                result.error.toWorkerResult()
            }
        }
    }

    companion object {
        const val RUN_ID = "run_id"
    }
}