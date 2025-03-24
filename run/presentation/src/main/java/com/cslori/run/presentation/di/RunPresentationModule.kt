package com.cslori.run.presentation.di

import com.cslori.run.domain.RunningTracker
import com.cslori.run.presentation.active_run.ActiveRunViewModel
import com.cslori.run.presentation.run_overview.RunOverViewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val runPresentationModule = module {
    singleOf(::RunningTracker)

    viewModelOf(::RunOverViewViewModel)
    viewModelOf(::ActiveRunViewModel)
}