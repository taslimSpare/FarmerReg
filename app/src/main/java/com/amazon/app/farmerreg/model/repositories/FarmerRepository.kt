package com.amazon.app.farmerreg.model.repositories

import android.app.Application
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.amazon.app.farmerreg.helper.Constants
import com.amazon.app.farmerreg.model.pojo.FarmerProfile
import com.amazon.app.farmerreg.model.roomDB.MyAppDatabase
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*


class FarmerRepository(application: Application) {


    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val fireStore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val roomDb: MyAppDatabase = MyAppDatabase.getAppDatabase(application)!!
    private val storageReference: StorageReference = FirebaseStorage.getInstance().reference
    var farmerLivedata = MutableLiveData<String>().apply { value = "" }



    // push farmer details to FireStore
    public fun uploadFarmerDetails(farmerProfile: FarmerProfile) {

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
    public fun downloadAllFarms() {

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


    public fun uploadFarmerPictureToFirebaseStorage(filePath: Uri) {
        val sr: StorageReference = storageReference.child("farmer_pictures/" + UUID.randomUUID().toString())
        val urlTask = sr.putFile(filePath)

        urlTask.addOnSuccessListener {
            val task: Task<Uri> = sr.downloadUrl
            task.addOnSuccessListener { farmerLivedata.value = ("${Constants.UPLOAD_USER_DETAILS_SUCCESSFUL}${Constants.SEPARATOR}${it.toString()}") }
            task.addOnFailureListener { farmerLivedata.value = "${Constants.FAILED}${Constants.SEPARATOR}${it.message}" }
        }

        urlTask.addOnFailureListener{
            farmerLivedata.value = "${Constants.FAILED}${Constants.SEPARATOR}${it.message}"
        }

    }

    // save user profile to Room
    private suspend fun saveFarmerToRoom(farmerProfile: FarmerProfile) {

        farmerLivedata.postValue("")

        roomDb.farmersDao().createFarmer(farmerProfile)
        farmerLivedata.postValue(Constants.SAVE_USER_TO_ROOM_SUCCESSFUL)
    }

}
