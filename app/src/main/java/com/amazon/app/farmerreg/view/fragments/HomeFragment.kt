package com.amazon.app.farmerreg.view.fragments


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.amazon.app.farmerreg.R
import com.amazon.app.farmerreg.databinding.FragmentHomeBinding
import com.amazon.app.farmerreg.databinding.FragmentOnboarderProfileBinding
import com.amazon.app.farmerreg.view.ExitActivity
import com.amazon.app.farmerreg.view.viewmodel.OnboarderVM

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var fragmentHomeBinding: FragmentHomeBinding
    private lateinit var onboarderVM: OnboarderVM


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        navController = Navigation.findNavController(container!!)
        onboarderVM = ViewModelProviders.of(activity as FragmentActivity).get(OnboarderVM::class.java)
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)


        // onBackPressed
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { ExitActivity().exit(requireActivity().applicationContext) }
        })


        fragmentHomeBinding.clAddNewFarmer.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_addNewFarmerFragment)
        }

        fragmentHomeBinding.clLatestNews.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://theagromall.com/")
            startActivity(intent)
        }

        fragmentHomeBinding.clMyFarmers.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_allFarmersFragment)
        }

        fragmentHomeBinding.clSupport.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("http://theagromall.com/")
            startActivity(intent)

        }


        // profile icon click
        fragmentHomeBinding.ivFarmer.setOnClickListener { navController.navigate(R.id.action_homeFragment_to_onboarderProfileFragment) }


        return fragmentHomeBinding.root
    }


}
