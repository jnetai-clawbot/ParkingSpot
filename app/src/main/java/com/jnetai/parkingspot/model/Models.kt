package com.jnetai.parkingspot.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parking_spots")
data class ParkingSpot(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val hourlyRate: Double = 0.0,
    val maxHours: Double = 0.0,
    val notes: String = "",
    val isFavourite: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "parking_sessions")
data class ParkingSession(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val spotId: Long,
    val startTime: Long = System.currentTimeMillis(),
    val endTime: Long = 0,
    val cost: Double = 0.0,
    val notes: String = ""
)