package com.amazon.app.farmerreg.view.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.amazon.app.farmerreg.model.pojo.FarmerProfile
import com.amazon.app.farmerreg.model.repositories.FarmerRepository


class FarmerVM(application: Application) : AndroidViewModel(application) {


    private val farmerRepository: FarmerRepository = FarmerRepository(application)
    lateinit var highlightedFarmer: FarmerProfile
    val farmerProfile: FarmerProfile = FarmerProfile("", "", "", "", "", 0, 0)


    fun farmerLivedata() : LiveData<String> { return farmerRepository.farmerLivedata }


    fun allFarmersLivedata() : LiveData<List<FarmerProfile>> { return farmerRepository.allfarmersLivedata }


    fun saveFarmerDetails(farmerProfile: FarmerProfile) { farmerRepository.uploadFarmerDetails(farmerProfile) }


    fun downloadAllFarmsFromFirebase() { farmerRepository.downloadAllFarmsFromFirebase() }


    fun downloadAllFarmsFromRoom() { farmerRepository.downloadAllFarmsFromRoom() }


    fun uploadFarmerPictureToFirebaseStorage(filePath: Uri) { farmerRepository.uploadFarmerPictureToFirebaseStorage(filePath) }


}
