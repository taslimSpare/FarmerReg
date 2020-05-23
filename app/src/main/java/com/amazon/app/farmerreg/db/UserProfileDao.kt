package com.amazon.app.farmerreg.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.amazon.app.farmerreg.pojo.UserProfile


@Dao
interface UserProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createUser(userProfile: UserProfile)

    @Query("SELECT * FROM profile ORDER BY name ASC")
    fun getUser() : List<UserProfile>

    @Query("DELETE from profile")
    fun nukeThisTable()

}