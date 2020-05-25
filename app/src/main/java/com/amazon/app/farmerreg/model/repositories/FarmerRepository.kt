package com.amazon.app.farmerreg.model.repositories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.amazon.app.farmerreg.helper.Constants
import com.amazon.app.farmerreg.model.pojo.FarmerProfile
import com.amazon.app.farmerreg.model.roomDB.MyAppDatabase
import com.amazon.app.farmerreg.model.pojo.UserProfile
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch


class FarmerRepository(application: Application) {


    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val roomDb: MyAppDatabase = MyAppDatabase.getAppDatabase(application)!!
    var farmerLivedata = MutableLiveData<String>().apply { value = "" }



    // push farmer details to FireStore
    private fun uploadFarmerDetails(farmerProfile: FarmerProfile) {

        farmerLivedata.value = ""

        val task: Task<Void> = fireStore.collection("farmer").document(mAuth.uid!!).set(farmerProfile)

        task.addOnSuccessListener {
            farmerLivedata.value = (Constants.UPLOAD_USER_DETAILS_SUCCESSFUL)
            CoroutineScope(IO).launch { saveFarmerToRoom(farmerProfile) }
        }
        task.addOnFailureListener {
            farmerLivedata.value = ("${Constants.FAILED}${Constants.SEPARATOR}${it.message}")
        }
    }


    // push user details to FireStore
    private fun downloadAllFarms() {

        farmerLivedata.value = ""

        val task: Task<QuerySnapshot> = fireStore.collection("farmer").get()

        task.addOnSuccessListener {

            val querySnapshot : QuerySnapshot = it

            if(!querySnapshot.isEmpty) {
                var farmers : List<FarmerProfile> = querySnapshot.toObjects(FarmerProfile::class.java)

                for (i in farmers) { CoroutineScope(IO).launch { saveFarmerToRoom(i) } }

                farmerLivedata.value = (Constants.DOWNLOAD_USER_DETAILS_SUCCESSFUL)
            }
        }
        task.addOnFailureListener { farmerLivedata.value = ("${Constants.FAILED}${Constants.SEPARATOR}${it.message}") }

    }


    // save user profile to Room
    private suspend fun saveFarmerToRoom(farmerProfile: FarmerProfile) {

        farmerLivedata.postValue("")

        roomDb.farmersDao().createFarmer(farmerProfile)
        farmerLivedata.postValue(Constants.SAVE_USER_TO_ROOM_SUCCESSFUL)
    }

}
