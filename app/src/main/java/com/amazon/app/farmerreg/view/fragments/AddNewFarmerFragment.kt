package com.amazon.app.farmerreg.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.amazon.app.farmerreg.R
import com.amazon.app.farmerreg.databinding.ActivityMainBinding
import com.amazon.app.farmerreg.databinding.FragmentAddNewFarmerBinding
import com.amazon.app.farmerreg.databinding.FragmentLoginBinding
import com.amazon.app.farmerreg.helper.Utils
import com.amazon.app.farmerreg.view.viewmodel.AuthVM

/**
 * A simple [Fragment] subclass.
 */
class AddNewFarmerFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var fragmentAddNewFarmerFragmentBinding: FragmentAddNewFarmerBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        navController = Navigation.findNavController(container!!)
        fragmentAddNewFarmerFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_farmer, container, false)


        // button save
        fragmentAddNewFarmerFragmentBinding.btnSaveFarmer.setOnClickListener {
            if(!Utils().errorfy(
                    fragmentAddNewFarmerFragmentBinding.tvFarmerName,
                    fragmentAddNewFarmerFragmentBinding.tvFarmerPhoneNumber,
                    fragmentAddNewFarmerFragmentBinding.tvFarmName,
                    fragmentAddNewFarmerFragmentBinding.tvFarmLocation) && fragmentAddNewFarmerFragmentBinding.tvFarmCoordinates.text.toString().trim() != "") {



            }
        }

        return fragmentAddNewFarmerFragmentBinding.root
    }


}
