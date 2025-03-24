package com.cslori.run.location

import android.location.Location
import com.cslori.core.domain.location.LocationWithAltitude

fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = com.cslori.core.domain.location.Location(
            latitude = latitude,
            longitude = longitude,
        ),
        altitude = altitude
    )
}