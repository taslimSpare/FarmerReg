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


class AuthRepository(application: Application) {


    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val roomDb: MyAppDatabase = MyAppDatabase.getAppDatabase(application)!!
    var authProgress = MutableLiveData<String>().apply { value = "" }



    // check if user is looged in
    fun isUserLoggedIn() : Boolean {
        return mAuth.currentUser != null
    }


    // sign in
    fun signIn(email: String, password: String) {

        authProgress.value = ""

        val task: Task<AuthResult> = mAuth.signInWithEmailAndPassword(email, password)

        task.addOnSuccessListener {
            authProgress.value = (Constants.SIGN_IN_SUCCESSFUL)
            downloadUserDetails()
        }

        task.addOnFailureListener {authProgress.value = ("${Constants.FAILED}${Constants.SEPARATOR}${it.message}")}
    }


    // sign up
    fun createUser(userProfile: UserProfile, password: String) {

        authProgress.value = ""

        val task: Task<AuthResult> = mAuth.createUserWithEmailAndPassword(userProfile.email, password)

        task.addOnSuccessListener {
            authProgress.value = (Constants.SIGN_UP_SUCCESSFUL)
            uploadUserDetails(userProfile)
        }

        task.addOnFailureListener {authProgress.value = ("${Constants.FAILED}${Constants.SEPARATOR}${it.message}")}
    }



    // push user details to FireStore
    private fun uploadUserDetails(userProfile: UserProfile) {

        authProgress.value = ""

        val task: Task<Void> = fireStore.collection("users").document(mAuth.uid!!).set(userProfile)

        task.addOnSuccessListener {
            authProgress.value = (Constants.UPLOAD_USER_DETAILS_SUCCESSFUL)
            CoroutineScope(IO).launch {
                saveUserProfileToRoom(userProfile)
            }
        }
        task.addOnFailureListener {
            authProgress.value = ("${Constants.FAILED}${Constants.SEPARATOR}${it.message}")
            mAuth.signOut()
        }
    }


    // push user details to FireStore
    private fun downloadUserDetails() {

        authProgress.value = ""

        val task: Task<DocumentSnapshot> = fireStore.collection("users").document(mAuth.uid!!).get()

        task.addOnSuccessListener {
            authProgress.value = (Constants.DOWNLOAD_USER_DETAILS_SUCCESSFUL)
            CoroutineScope(IO).launch {
                saveUserProfileToRoom(it.toObject(UserProfile::class.java)!!)
            }
        }
        task.addOnFailureListener {
            authProgress.value = ("${Constants.FAILED}${Constants.SEPARATOR}${it.message}")
            mAuth.signOut()
        }
    }


    // save user profile to Room
    private suspend fun saveUserProfileToRoom(userProfile: UserProfile) {

        authProgress.postValue("")

        roomDb.userProfileDao().createUser(userProfile)
        authProgress.postValue(Constants.SAVE_USER_TO_ROOM_SUCCESSFUL)
    }

}
