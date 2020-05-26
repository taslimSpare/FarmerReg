package com.amazon.app.farmerreg.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.amazon.app.farmerreg.R
import com.amazon.app.farmerreg.databinding.FragmentAddNewFarmerBinding
import com.amazon.app.farmerreg.helper.Constants
import com.amazon.app.farmerreg.helper.Utils
import com.amazon.app.farmerreg.model.pojo.FarmerProfile
import com.amazon.app.farmerreg.view.viewmodel.FarmerVM

/**
 * A simple [Fragment] subclass.
 */
class AddNewFarmer_farmer_detailsFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var fragmentAddNewFarmerFragmentBinding: FragmentAddNewFarmerBinding
    private lateinit var farmerVM: FarmerVM


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        navController = Navigation.findNavController(container!!)
        fragmentAddNewFarmerFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_farmer_farmer_details, container, false)
        farmerVM = ViewModelProviders.of(activity as FragmentActivity).get(FarmerVM::class.java)


        fragmentAddNewFarmerFragmentBinding.tvFarmCoordinates.setOnClickListener {

        }



        // button save
        fragmentAddNewFarmerFragmentBinding.btnSaveFarmer.setOnClickListener {
            if(!Utils().errorfyEdittexts(
                    fragmentAddNewFarmerFragmentBinding.tvFarmerName,
                    fragmentAddNewFarmerFragmentBinding.tvFarmerPhoneNumber,
                    fragmentAddNewFarmerFragmentBinding.tvFarmName,
                    fragmentAddNewFarmerFragmentBinding.tvFarmLocation) && !Utils().errorfyTextViews(fragmentAddNewFarmerFragmentBinding.tvFarmCoordinates)) {

                setObserver()
                showProgressBar()

                farmerVM.saveFarmerDetails(FarmerProfile(
                    fragmentAddNewFarmerFragmentBinding.tvFarmerName.text.toString().trim(),
                    fragmentAddNewFarmerFragmentBinding.tvFarmerPhoneNumber.text.toString().trim(),
                    fragmentAddNewFarmerFragmentBinding.tvFarmName.text.toString().trim(),
                    fragmentAddNewFarmerFragmentBinding.tvFarmLocation.text.toString(),
                    "",
                    23,
                    12))

            }
        }

        return fragmentAddNewFarmerFragmentBinding.root
    }


    private fun setObserver() {
        farmerVM.farmerLivedata().observe(viewLifecycleOwner, Observer {

            if(it.contains(Constants.SAVE_USER_TO_ROOM_SUCCESSFUL)) {
                Toast.makeText(requireActivity().applicationContext, "Successful", Toast.LENGTH_LONG).show()
                navController.navigate(R.id.action_addNewFarmerFragment_to_homeFragment)
            }
            else if(it.contains(Constants.FAILED)) {
                hideProgressBar(it.split(Constants.SEPARATOR)[1])
            }
        })
    }


    private fun showProgressBar() {
        fragmentAddNewFarmerFragmentBinding.tvFarmerName.isEnabled = false
        fragmentAddNewFarmerFragmentBinding.tvFarmerPhoneNumber.isEnabled = false
        fragmentAddNewFarmerFragmentBinding.tvFarmName.isEnabled = false
        fragmentAddNewFarmerFragmentBinding.tvFarmLocation.isEnabled = false
        fragmentAddNewFarmerFragmentBinding.tvFarmCoordinates.isEnabled = false
        fragmentAddNewFarmerFragmentBinding.progressView.loadingContainer.visibility = View.VISIBLE
    }

    private fun hideProgressBar(error: String) {
        fragmentAddNewFarmerFragmentBinding.tvFarmerName.isEnabled = true
        fragmentAddNewFarmerFragmentBinding.tvFarmerPhoneNumber.isEnabled = true
        fragmentAddNewFarmerFragmentBinding.tvFarmName.isEnabled = true
        fragmentAddNewFarmerFragmentBinding.tvFarmLocation.isEnabled = true
        fragmentAddNewFarmerFragmentBinding.tvFarmCoordinates.isEnabled = true
        fragmentAddNewFarmerFragmentBinding.progressView.loadingContainer.visibility = View.GONE
        Toast.makeText(activity?.applicationContext, error, Toast.LENGTH_LONG).show()
    }

}
