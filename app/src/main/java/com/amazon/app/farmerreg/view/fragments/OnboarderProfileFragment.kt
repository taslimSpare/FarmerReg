package com.amazon.app.farmerreg.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation

import com.amazon.app.farmerreg.R
import com.amazon.app.farmerreg.databinding.FragmentLoginBinding
import com.amazon.app.farmerreg.databinding.FragmentOnboarderProfileBinding
import com.amazon.app.farmerreg.view.ExitActivity
import com.amazon.app.farmerreg.view.viewmodel.AuthVM
import com.amazon.app.farmerreg.view.viewmodel.OnboarderVM

/**
 * A simple [Fragment] subclass.
 */
class OnboarderProfileFragment : Fragment() {


    private lateinit var navController: NavController
    private lateinit var fragmentOnboarderProfileBinding: FragmentOnboarderProfileBinding
    private lateinit var onboarderVM: OnboarderVM


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        navController = Navigation.findNavController(container!!)
        onboarderVM = ViewModelProviders.of(activity as FragmentActivity).get(OnboarderVM::class.java)
        fragmentOnboarderProfileBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_onboarder_profile, container, false)


        // bind userdata from room to XML
        onboarderVM.getUserProfile()
        onboarderVM.authLiveData().observe(viewLifecycleOwner, Observer { fragmentOnboarderProfileBinding.userProfile = it })


        // onBackPressed
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { navController.navigate(R.id.action_onboarderProfileFragment_to_homeFragment) }
        })


        // signOut clicked
        fragmentOnboarderProfileBinding.signOut.setOnClickListener {
            onboarderVM.signOut()
            navController.navigate(R.id.action_onboarderProfileFragment_to_loginFragment)
        }



        return fragmentOnboarderProfileBinding.root
    }

}
