package com.amazon.app.farmerreg.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amazon.app.farmerreg.R
import com.amazon.app.farmerreg.db.pojo.UserProfile

@Database(version = 1, entities = [UserProfile::class], exportSchema = false)
abstract class MyAppDatabase : RoomDatabase() {

    abstract fun userProfileDao(): UserProfileDao

    companion object {
        private var sInstance: MyAppDatabase? = null

        fun getAppDatabase(context: Context): MyAppDatabase? {
            if (sInstance == null) {
                synchronized(MyAppDatabase::class) {
                    sInstance = buildDatabase(context)
                }
            }
            return sInstance
        }

        private fun buildDatabase(context: Context): MyAppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                MyAppDatabase::class.java, context.getString(R.string.app_name)).build()
        }
    }
}