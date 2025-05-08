package com.cslori.analytics.data.di

import com.cslori.analytics.data.RoomAnalyticsRepository
import com.cslori.analytics.domain.AnalyticsRepository
import com.cslori.core.database.RunDatabase
import com.cslori.core.database.dao.AnalyticsDao
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val analyticsModule = module {
    singleOf(::RoomAnalyticsRepository).bind<AnalyticsRepository>()
    single {
        get<RunDatabase>().analyticsDao
    }
}