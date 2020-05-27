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
import com.amazon.app.farmerreg.databinding.FragmentAddNewFarmerFarmerDetailsBinding
import com.amazon.app.farmerreg.databinding.FragmentFarmerProfileBinding
import com.amazon.app.farmerreg.view.viewmodel.FarmerVM
import com.bumptech.glide.Glide

/**
 * A simple [Fragment] subclass.
 */
class FarmerProfileFragment : Fragment() {


    private lateinit var navController: NavController
    private lateinit var fragmentFarmerProfileBinding: FragmentFarmerProfileBinding
    private lateinit var farmerVM: FarmerVM


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        navController = Navigation.findNavController(container!!)
        fragmentFarmerProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_farmer_profile, container, false)
        farmerVM = ViewModelProviders.of(activity as FragmentActivity).get(FarmerVM::class.java)

        fragmentFarmerProfileBinding.farmerProfile = farmerVM.highlightedFarmer



        fragmentFarmerProfileBinding.ibBack.setOnClickListener { navController.navigate(R.id.action_farmerProfileFragment_to_allFarmersFragment) }

        Glide
            .with(requireActivity().applicationContext)
            .load(farmerVM.highlightedFarmer.farmerImageUrl)
            .centerCrop()
            .placeholder(R.drawable.farmer)
            .into(fragmentFarmerProfileBinding.ivProfileImage)

        return fragmentFarmerProfileBinding.root
    }

}