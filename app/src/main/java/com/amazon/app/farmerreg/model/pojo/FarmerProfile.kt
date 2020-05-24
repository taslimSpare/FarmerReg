package com.amazon.app.farmerreg.model.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "farmer")
data class FarmerProfile(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var farmerName: String,
    var farmerPhoneNumber: String,
    var farmName: String,
    var farmLocation: String,
    var farmLat: Int = 0,
    var farmLong: Int = 0)