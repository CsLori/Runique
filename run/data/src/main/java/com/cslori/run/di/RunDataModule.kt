package com.cslori.run.di

import com.cslori.run.data.CreateRunWorker
import com.cslori.run.data.DeleteRunWorker
import com.cslori.run.data.FetchRunsWorker
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

val runDataModule = module {
    workerOf(::CreateRunWorker)
    workerOf(::FetchRunsWorker)
    workerOf(::DeleteRunWorker)
}