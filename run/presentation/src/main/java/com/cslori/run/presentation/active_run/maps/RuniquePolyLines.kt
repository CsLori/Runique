package com.cslori.run.presentation.active_run.maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.cslori.core.domain.location.LocationTimestamp
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Polyline
import timber.log.Timber

@Composable
fun RuniquePolyLines(locations: List<List<LocationTimestamp>>) {
    val polyLines = remember(locations) {
        locations.map {
            it.zipWithNext { timeStamp1, timeStamp2 ->
                PolyLineUi(
                    location1 = timeStamp1.location.location,
                    location2 = timeStamp2.location.location,
                    color = PolylineColorCalculator.locationsToColor(
                        location1 = timeStamp1,
                        location2 = timeStamp2
                    )
                )
            }
        }
    }

    polyLines.forEach { polyLine ->
        polyLine.forEach { polyLineUi ->
            Timber.d("PolyLineUi: ${polyLineUi.color}")
            Polyline(
                points = listOf(
                    LatLng(polyLineUi.location1.lat, polyLineUi.location1.long),
                    LatLng(polyLineUi.location2.lat, polyLineUi.location2.long),

                    ),
                color = polyLineUi.color,
                jointType = JointType.BEVEL
            )
        }
    }
}