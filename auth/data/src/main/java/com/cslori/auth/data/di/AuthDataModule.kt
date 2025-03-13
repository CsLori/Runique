package com.cslori.auth.data.di

import com.cslori.auth.data.EmailPatternValidator
import com.cslori.auth.data.repository.AuthRepositoryImpl
import com.cslori.auth.domain.PatternValidator
import com.cslori.auth.domain.UserDataValidator
import com.cslori.auth.domain.repository.AuthRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authDataModule = module {
    single<PatternValidator> {
        EmailPatternValidator
    }
    singleOf(::UserDataValidator)
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()

}