package com.jnetai.parkingspot.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jnetai.parkingspot.model.ParkingSpot
import com.jnetai.parkingspot.model.ParkingSession

@Database(entities = [ParkingSpot::class, ParkingSession::class], version = 1, exportSchema = false)
abstract class ParkingDatabase : RoomDatabase() {
    abstract fun dao(): ParkingDao
    companion object {
        @Volatile private var INSTANCE: ParkingDatabase? = null
        fun getInstance(context: Context): ParkingDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(context, ParkingDatabase::class.java, "parking_spot.db")
                .fallbackToDestructiveMigration().build().also { INSTANCE = it }
        }
    }
}