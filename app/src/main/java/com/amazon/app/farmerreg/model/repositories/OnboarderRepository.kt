package com.amazon.app.farmerreg.model.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.amazon.app.farmerreg.helper.Constants
import com.amazon.app.farmerreg.model.roomDB.MyAppDatabase
import com.amazon.app.farmerreg.model.pojo.UserProfile
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class OnboarderRepository(application: Application) {


    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val roomDb: MyAppDatabase = MyAppDatabase.getAppDatabase(application)!!
    var userLiveData = MutableLiveData<UserProfile>().apply { value = UserProfile() }



    public fun getUserProfile() {
        CoroutineScope(IO).launch { fetchUserProfileFromRoom() }
    }


    public fun signOut() {
        CoroutineScope(IO).launch { deleteUser() }
    }




    // fetch user profile from Room
    private suspend fun fetchUserProfileFromRoom() {
        userLiveData.postValue(roomDb.userProfileDao().getUser()[0])
    }


    // delete user from room
    private suspend fun deleteUser() {
        roomDb.userProfileDao().nukeThisTable()
        roomDb.farmersDao().nukeThisTable()
        mAuth.signOut()
    }

}
