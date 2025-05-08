package com.cslori.analytics.data

import androidx.room.Query
import com.cslori.analytics.domain.AnalyticsRepository
import com.cslori.analytics.domain.AnalyticsValues
import com.cslori.core.database.dao.AnalyticsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class RoomAnalyticsRepository(
    private val analyticsDao: AnalyticsDao
): AnalyticsRepository {
    override suspend fun getAnalyticsValues(): AnalyticsValues {
        return withContext(Dispatchers.IO) {
            val totalDistance = async { analyticsDao.getTotalDistance() }
            val totalTimeMillis = async { analyticsDao.getTotalTime() }
            val maxRunSpeed = async { analyticsDao.getMaxRunSpeed() }
            val avgDistancePerRun = async { analyticsDao.getAvgDistancePerRun() }
            val avgPacePerRun = async { analyticsDao.getAvgPacePerRun() }
            AnalyticsValues(
                totalDistanceRun = totalDistance.await(),
                totalTimeRun = totalTimeMillis.await().milliseconds,
                fastestEverRun = maxRunSpeed.await(),
                avgDistancePerRun = avgDistancePerRun.await(),
                avgPacePerRun = avgPacePerRun.await()
            )
        }
    }
}



//@Query("SELECT SUM(distanceMeters) FROM runentity")
//suspend fun getTotalDistance(): Int
//
//@Query("SELECT SUM(durationMillis) FROM runentity")
//suspend fun getTotalTime(): Long
//
//@Query("SELECT MAX(maxSpeedKmh) FROM runentity")
//suspend fun getMaxRunSpeed(): Double
//
//@Query("SELECT AVG(distanceMeters) FROM runentity")
//suspend fun getAvgDistancePerRun(): Double
//
//@Query("SELECT AVG(durationMillis/ 6000.0)/ (distanceMeters / 1000.0) FROM runentity")
//suspend fun getAvgPacePerRun(): Double