package com.cslori.run.presentation.di

import com.cslori.run.presentation.active_run.ActiveRunViewModel
import com.cslori.run.presentation.run_overview.RunOverViewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val runViewModelModule = module {
    viewModelOf(::RunOverViewViewModel)
    viewModelOf(::ActiveRunViewModel)
}