package com.jnetai.parkingspot.data

import androidx.room.*
import com.jnetai.parkingspot.model.ParkingSpot
import com.jnetai.parkingspot.model.ParkingSession

@Dao
interface ParkingDao {
    @Query("SELECT * FROM parking_spots ORDER BY name") suspend fun getAllSpots(): List<ParkingSpot>
    @Query("SELECT * FROM parking_spots WHERE isFavourite = 1") suspend fun getFavourites(): List<ParkingSpot>
    @Query("SELECT * FROM parking_spots WHERE id = :id") suspend fun getSpot(id: Long): ParkingSpot?
    @Insert suspend fun insertSpot(spot: ParkingSpot): Long
    @Update suspend fun updateSpot(spot: ParkingSpot)
    @Delete suspend fun deleteSpot(spot: ParkingSpot)

    @Query("SELECT * FROM parking_sessions ORDER BY startTime DESC") suspend fun getAllSessions(): List<ParkingSession>
    @Insert suspend fun insertSession(session: ParkingSession): Long
    @Update suspend fun updateSession(session: ParkingSession)
    @Delete suspend fun deleteSession(session: ParkingSession)
}