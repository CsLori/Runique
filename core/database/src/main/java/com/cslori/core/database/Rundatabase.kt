package com.cslori.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cslori.core.database.dao.AnalyticsDao
import com.cslori.core.database.dao.RunDao
import com.cslori.core.database.dao.RunPendingSyncDao
import com.cslori.core.database.entity.DeletedRunSyncEntity
import com.cslori.core.database.entity.RunEntity
import com.cslori.core.database.entity.RunPendingSyncEntity

@Database(
    entities = [RunEntity::class, RunPendingSyncEntity::class, DeletedRunSyncEntity::class],
    version = 2
)

abstract class RunDatabase : RoomDatabase() {

    abstract val runDao: RunDao
    abstract val runPendingSyncDao: RunPendingSyncDao
    abstract val analyticsDao: AnalyticsDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {

    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS RunPendingSyncEntity (
                durationMillis INTEGER NOT NULL,
                distanceMeters INTEGER NOT NULL,
                dateTimeUtc TEXT NOT NULL,
                latitude REAL NOT NULL,
                longitude REAL NOT NULL,
                avgSpeedKmh REAL NOT NULL,
                maxSpeedKmh REAL NOT NULL,
                totalElevationMeters INTEGER NOT NULL,
                mapPictureUrl TEXT,
                id TEXT NOT NULL,
                runId TEXT NOT NULL,
                mapPictureBytes BLOB NOT NULL,
                userId TEXT NOT NULL,
                PRIMARY KEY(runId)
            )
            """.trimIndent()
        )

        // Create DeletedRunSyncEntity table
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS DeletedRunSyncEntity (
                runId TEXT NOT NULL,
                userId TEXT NOT NULL,
                PRIMARY KEY(runId)
            )
            """.trimIndent()
        )
    }
}