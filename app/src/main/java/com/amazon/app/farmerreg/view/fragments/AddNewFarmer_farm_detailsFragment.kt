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
import com.amazon.app.farmerreg.databinding.FragmentAddNewFarmerFarmDetailsBinding
import com.amazon.app.farmerreg.helper.Utils
import com.amazon.app.farmerreg.view.viewmodel.FarmerVM

/**
 * A simple [Fragment] subclass.
 */
class AddNewFarmer_farm_detailsFragment : Fragment() {


    private lateinit var navController: NavController
    private lateinit var fragmentAddNewFarmerFarmDetailsBinding: FragmentAddNewFarmerFarmDetailsBinding
    private lateinit var farmerVM: FarmerVM


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        navController = Navigation.findNavController(container!!)
        fragmentAddNewFarmerFarmDetailsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_farmer_farm_details, container, false)
        farmerVM = ViewModelProviders.of(activity as FragmentActivity).get(FarmerVM::class.java)


        // user presses the back button
        fragmentAddNewFarmerFarmDetailsBinding.tvBack.setOnClickListener { navController.navigateUp() }


        // user clicks on Farm coordinates
        fragmentAddNewFarmerFarmDetailsBinding.etCoordinates.setOnClickListener {
            // todo: Launch Maps fragment
        }

        // button next
        fragmentAddNewFarmerFarmDetailsBinding.btnNext.setOnClickListener {
            if(!Utils().errorfyEdittexts(
                    fragmentAddNewFarmerFarmDetailsBinding.etFarmName,
                    fragmentAddNewFarmerFarmDetailsBinding.etFarmLocation) && !Utils().errorfyTextViews(fragmentAddNewFarmerFarmDetailsBinding.etCoordinates)) {

                farmerVM.farmerProfile.farmName = fragmentAddNewFarmerFarmDetailsBinding.etFarmName.text.toString().trim()
                farmerVM.farmerProfile.farmLocation = fragmentAddNewFarmerFarmDetailsBinding.etFarmLocation.text.toString().trim()
                farmerVM.farmerProfile.farmLat = fragmentAddNewFarmerFarmDetailsBinding.etCoordinates.text.toString().trim().split(",")[0].toLong()
                farmerVM.farmerProfile.farmLong = fragmentAddNewFarmerFarmDetailsBinding.etCoordinates.text.toString().trim().split(",")[1].toLong()

                navController.navigate(R.id.action_addNewFarmer_farm_detailsFragment_to_addNewFarmer_photoFragment)
            }
        }

        return fragmentAddNewFarmerFarmDetailsBinding.root
    }

}
