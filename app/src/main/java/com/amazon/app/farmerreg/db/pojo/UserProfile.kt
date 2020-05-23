package com.amazon.app.farmerreg.db.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class UserProfile(@PrimaryKey var id: Int = 0, var name: String = "", var email: String = "", var phone: String = "") {

    constructor() : this(0)
}