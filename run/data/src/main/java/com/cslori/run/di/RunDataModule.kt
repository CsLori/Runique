package com.cslori.run.di

import com.cslori.core.domain.run.SyncRunScheduler
import com.cslori.run.data.CreateRunWorker
import com.cslori.run.data.DeleteRunWorker
import com.cslori.run.data.FetchRunsWorker
import com.cslori.run.data.SyncRunWorkerScheduler
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)

    singleOf(::SyncRunWorkerScheduler).bind<SyncRunScheduler>()
}