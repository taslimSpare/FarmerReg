package com.amazon.app.farmerreg.model.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "farmer")
data class FarmerProfile(
    var farmerName: String,
    var farmerPhoneNumber: String,
    var farmName: String,
    var farmLocation: String,
    var farmerImageUrl: String,
    var farmLat: Long = 0,
    var farmLong: Long = 0) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}