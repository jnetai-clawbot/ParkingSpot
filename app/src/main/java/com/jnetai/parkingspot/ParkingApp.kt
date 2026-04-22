package com.jnetai.parkingspot

import android.app.Application
import com.jnetai.parkingspot.data.ParkingDatabase

class ParkingApp : Application() {
    val database by lazy { ParkingDatabase.getInstance(this) }
}