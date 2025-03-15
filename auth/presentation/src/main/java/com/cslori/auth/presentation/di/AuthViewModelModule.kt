package com.cslori.auth.presentation.di

import com.cslori.auth.presentation.login.LoginViewModel
import com.cslori.auth.presentation.register.RegisterViewmodel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val authViewModelModule = module {
    viewModelOf(::RegisterViewmodel)
    viewModelOf(::LoginViewModel)
}