package com.amazon.app.farmerreg.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.amazon.app.farmerreg.model.pojo.UserProfile
import com.amazon.app.farmerreg.model.repositories.AuthRepository
import com.amazon.app.farmerreg.model.repositories.OnboarderRepository


class OnboarderVM(application: Application) : AndroidViewModel(application) {


    private val onboarderRepository: OnboarderRepository = OnboarderRepository(application)


    fun authLiveData() : LiveData<UserProfile> { return onboarderRepository.userLiveData }

    fun getUserProfile() { onboarderRepository.getUserProfile() }

}
