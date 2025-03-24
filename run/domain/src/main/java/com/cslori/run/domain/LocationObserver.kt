package com.cslori.run.domain

import com.cslori.core.domain.location.LocationWithAltitude
import kotlinx.coroutines.flow.Flow

interface LocationObserver {
    fun observeLocation(interval :Long) : Flow<LocationWithAltitude>
}