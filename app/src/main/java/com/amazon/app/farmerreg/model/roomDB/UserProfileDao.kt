package com.amazon.app.farmerreg.model.roomDB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amazon.app.farmerreg.model.pojo.UserProfile


@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(userProfile: UserProfile)

    @Query("SELECT * FROM profile ORDER BY name ASC")
    fun getUser() : List<UserProfile>

    @Query("UPDATE profile SET farmers = farmers + 1 WHERE id = 0")
    fun incrementFarmer()

    @Query("DELETE from profile")
    fun nukeThisTable()

}