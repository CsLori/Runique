package com.cslori.core.data.di

import com.cslori.core.data.auth.EncryptedSessionStorage
import com.cslori.core.data.networking.HttpClientFactory
import com.cslori.core.data.repository.OfflineFirstRunRepository
import com.cslori.core.domain.SessionStorage
import com.cslori.core.domain.run.RunRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory(get()).build()
    }

    singleOf(::EncryptedSessionStorage).bind<SessionStorage>()

    singleOf(::OfflineFirstRunRepository).bind<RunRepository>()
}