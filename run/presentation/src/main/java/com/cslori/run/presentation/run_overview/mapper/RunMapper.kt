package com.cslori.run.presentation.run_overview.mapper

import com.cslori.core.domain.run.Run
import com.cslori.presentation.ui.formatted
import com.cslori.presentation.ui.toFormattedKm
import com.cslori.presentation.ui.toFormattedKmH
import com.cslori.presentation.ui.toFormattedMeters
import com.cslori.presentation.ui.toFormattedPace
import com.cslori.run.presentation.run_overview.model.RunUi
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun Run.toRunUi(): RunUi {
    val dateTimeInLocalTime = dateTimeUtc.withZoneSameInstant(ZoneId.systemDefault())
    val formattedDateTime = DateTimeFormatter.ofPattern("MMM dd,yyyy - hh:mma").format(dateTimeInLocalTime)

    val distanceKm = distanceMeters / 1000.0
    return RunUi(
        id = id!!,
        duration = duration.formatted(),
        dateTime = formattedDateTime,
        distance = distanceKm.toFormattedKm(),
        avgSpeed = avgSpeedKmh.toFormattedKmH(),
        maxSpeed = maxSpeedKmh.toFormattedKmH(),
        pace = duration.toFormattedPace(distanceKm),
        totalElevation = totalElevationMeters.toFormattedMeters(),
        mapPictureUrl = mapPictureUrl
    )
}