package com.cslori.run.network

import android.R.attr.duration
import com.cslori.core.domain.location.Location
import com.cslori.core.domain.run.Run
import kotlinx.serialization.InternalSerializationApi
import java.time.Instant
import java.time.ZoneId
import kotlin.time.Duration.Companion.milliseconds

@InternalSerializationApi
fun RunDto.toRun(): Run {
    return Run(
        id = id,
        duration = durationMillis.milliseconds,
        dateTimeUtc = Instant.parse(dateTimeUtc).atZone(ZoneId.of("UTC")),
        distanceMeters = distanceMeters,
        location = Location(
            lat, long
        ),
        maxSpeedKmh = maxSpeedKmh,
        totalElevationMeters = totalElevationMeters,
        mapPictureUrl = mapPictureUrl
    )
}

@OptIn(InternalSerializationApi::class)
fun Run.toCreateRunRequest(): CreateRunRequest {
    return CreateRunRequest(
        id = id!!,
        durationMillis = duration.inWholeMilliseconds,
        distanceMeters = distanceMeters,
        lat = location.lat,
        long = location.long,
        avgSpeedKmh = avgSpeedKmh,
        maxSpeedKmh = maxSpeedKmh,
        totalElevationMeters = totalElevationMeters,
        epochMillis = dateTimeUtc.toEpochSecond() * 1000L,
    )
}