package com.amazon.app.farmerreg.view.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.amazon.app.farmerreg.model.pojo.UserProfile
import com.amazon.app.farmerreg.model.repositories.AuthRepository



class AuthVM(application: Application) : AndroidViewModel(application) {


    private val authRepository: AuthRepository = AuthRepository(application)


    fun isUserLoggedIn() : Boolean {
        return authRepository.isUserLoggedIn()
    }

    fun authLiveData() : LiveData<String> { return authRepository.authProgress }

    fun logIn(email: String, password: String) { authRepository.signIn(email, password) }

    fun signUp(userProfile: UserProfile, password: String) { authRepository.createUser(userProfile, password) }


}
