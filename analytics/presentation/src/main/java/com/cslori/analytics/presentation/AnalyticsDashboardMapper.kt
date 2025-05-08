package com.cslori.analytics.presentation

import com.cslori.analytics.domain.AnalyticsValues
import com.cslori.presentation.ui.formatted
import com.cslori.presentation.ui.toFormattedKm
import com.cslori.presentation.ui.toFormattedMeters
import com.cslori.presentation.ui.toFormattedPace
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

fun Duration.toFormattedTotalTime() : String {
    val days = toLong(DurationUnit.DAYS)
    val hours = toLong(DurationUnit.HOURS) % 24
    val minutes = toLong(DurationUnit.MINUTES) % 60

    return "${days}d ${hours}h ${minutes}m"
}

fun AnalyticsValues.toAnalyticsDashboardState(): AnalyticsDashboardState {
    return AnalyticsDashboardState(
        totalDistanceRun = (totalDistanceRun / 1000.0).toFormattedKm(),
        totalTimeRun = totalTimeRun.toFormattedTotalTime(),
        fastestEverRun = fastestEverRun.toFormattedKm(),
        avgDistance = (avgDistancePerRun / 1000.0).toFormattedKm(),
        avgPace = avgPacePerRun.seconds.formatted()
    )

}