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
import com.amazon.app.farmerreg.databinding.FragmentAddNewFarmerFarmerDetailsBinding
import com.amazon.app.farmerreg.helper.Utils
import com.amazon.app.farmerreg.view.viewmodel.FarmerVM

/**
 * A simple [Fragment] subclass.
 */
class AddNewFarmerFarmerDetailsFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var fragmentAddNewFarmerFragmentBinding: FragmentAddNewFarmerFarmerDetailsBinding
    private lateinit var farmerVM: FarmerVM


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        navController = Navigation.findNavController(container!!)
        fragmentAddNewFarmerFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_farmer_farmer_details, container, false)
        farmerVM = ViewModelProviders.of(activity as FragmentActivity).get(FarmerVM::class.java)


        // user presses the back button
        fragmentAddNewFarmerFragmentBinding.tvBack.setOnClickListener { navController.navigateUp() }


        // button next
        fragmentAddNewFarmerFragmentBinding.btnNext.setOnClickListener {
            if(!Utils().errorfyEdittexts(
                    fragmentAddNewFarmerFragmentBinding.etFarmerName,
                    fragmentAddNewFarmerFragmentBinding.etFarmerPhoneNumber)) {

                farmerVM.farmerProfile.farmerName = fragmentAddNewFarmerFragmentBinding.etFarmerName.text.toString().trim()
                farmerVM.farmerProfile.farmerPhoneNumber = fragmentAddNewFarmerFragmentBinding.etFarmerPhoneNumber.text.toString().trim()

                navController.navigate(R.id.action_addNewFarmerFragment_to_addNewFarmer_farm_detailsFragment)
            }
        }

        return fragmentAddNewFarmerFragmentBinding.root
    }

}
