package com.example.gaenari.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "running_sessions")
data class RunningSession(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val distance: Double,
    val averageHeartRate: Float,
    val averageSpeed: Float,
    val timestamp: Long
)