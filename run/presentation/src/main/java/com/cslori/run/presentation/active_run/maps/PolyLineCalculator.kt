package com.cslori.run.presentation.active_run.maps

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.ColorUtils
import com.cslori.core.domain.location.LocationTimestamp
import kotlin.math.abs

object PolyLineCalculator {

    fun locationsToColor(location1: LocationTimestamp, location2: LocationTimestamp): Color {
        val distanceMeters = location1.location.location.distanceTo(location2.location.location)
        val timeDiff = abs((location2.durationTimestamp - location1.durationTimestamp).inWholeMilliseconds)
        val speedKmh = (distanceMeters / timeDiff) * 3.6

        return interpolateColor(
            speedKmh = speedKmh,
            minSpeed = 5.0,
            maxSpeed = 20.0,
            colorStart = Color.Green,
            colorMid = Color.Yellow,
            colorEnd = Color.Red
        )
    }
    private fun interpolateColor(
        speedKmh: Double,
        minSpeed: Double,
        maxSpeed: Double,
        colorStart: Color,
        colorMid: Color,
        colorEnd: Color
    ): Color {
        val ratio = ((speedKmh - minSpeed) / (maxSpeed - minSpeed)).coerceIn(0.0..1.0)
        val colorInt = if (ratio <= 0.5) {
            val midRatio = ratio / 0.5
            ColorUtils.blendARGB(colorStart.value.toInt(), colorMid.value.toInt(), midRatio.toFloat())
        } else {
            val midToEndRatio = (ratio - 0.5) / 0.5
            ColorUtils.blendARGB(colorMid.value.toInt(), colorEnd.value.toInt(), midToEndRatio.toFloat())
        }
        return Color(colorInt)
    }
}