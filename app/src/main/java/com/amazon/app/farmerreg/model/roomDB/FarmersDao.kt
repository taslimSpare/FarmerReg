package com.amazon.app.farmerreg.model.roomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amazon.app.farmerreg.model.pojo.FarmerProfile
import com.amazon.app.farmerreg.model.pojo.UserProfile


@Dao
interface FarmersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createFarmer(farmerProfile: FarmerProfile)

    @Query("SELECT * FROM farmer ORDER BY farmerName ASC")
    fun getFarmers() : List<FarmerProfile>

    @Query("DELETE from farmer")
    fun nukeThisTable()

}