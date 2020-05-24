package com.amazon.app.farmerreg.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.amazon.app.farmerreg.R
import com.amazon.app.farmerreg.databinding.ActivityMainBinding
import com.amazon.app.farmerreg.view.viewmodel.AuthVM

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var authVM: AuthVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.container)

        authVM = ViewModelProviders.of(this as FragmentActivity).get(AuthVM::class.java)

    }
}
