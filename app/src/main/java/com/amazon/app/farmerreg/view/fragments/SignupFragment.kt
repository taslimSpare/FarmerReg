@file:Suppress("DEPRECATION")

package com.amazon.app.farmerreg.view.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.amazon.app.farmerreg.R
import com.amazon.app.farmerreg.databinding.FragmentSignupBinding
import com.amazon.app.farmerreg.helper.Constants
import com.amazon.app.farmerreg.helper.Utils
import com.amazon.app.farmerreg.model.pojo.UserProfile
import com.amazon.app.farmerreg.view.viewmodel.AuthVM

/**
 * A simple [Fragment] subclass.
 */
class SignupFragment : Fragment() {


    private lateinit var navController: NavController
    private lateinit var fragmentSignupBinding: FragmentSignupBinding
    private lateinit var authVM: AuthVM


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        navController = Navigation.findNavController(container!!)
        authVM = ViewModelProviders.of(activity as FragmentActivity).get(AuthVM::class.java)
        fragmentSignupBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)


        // onBackPressed
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.navigate(R.id.action_signupFragment_to_loginFragment)
            }
        })


        // when sign up button is clicked
        fragmentSignupBinding.switchToSignIn.setOnClickListener { navController.navigate(R.id.action_signupFragment_to_loginFragment) }


        // when done/sign-in button is clicked
        fragmentSignupBinding.btnSignUp.setOnClickListener {
            if(!(Utils().errorfy(fragmentSignupBinding.etName, fragmentSignupBinding.etEmail, fragmentSignupBinding.etPassword))) {
                showProgressBar()
                authVM.signUp(UserProfile(0, fragmentSignupBinding.etName.text.toString().trim(), fragmentSignupBinding.etEmail.text.toString().trim(), 0), fragmentSignupBinding.etPassword.text.toString().trim())
                observe()
            }
        }

        return fragmentSignupBinding.root
    }



    private fun observe() {
        authVM.authLiveData().observe(viewLifecycleOwner, Observer {

            if(it.contains(Constants.SAVE_USER_TO_ROOM_SUCCESSFUL)) {
                navController.navigate(R.id.action_signupFragment_to_homeFragment)
            }
            else if(it.contains(Constants.FAILED)) {
                hideProgressBar(it.split(Constants.SEPARATOR)[1])
            }
        })
    }




    private fun showProgressBar() {
        fragmentSignupBinding.etName.isEnabled = false
        fragmentSignupBinding.etEmail.isEnabled = false
        fragmentSignupBinding.etPassword.isEnabled = false
        fragmentSignupBinding.progressView.loadingContainer.visibility = View.VISIBLE
    }

    private fun hideProgressBar(error: String) {
        fragmentSignupBinding.etEmail.isEnabled = true
        fragmentSignupBinding.etPassword.isEnabled = true
        fragmentSignupBinding.progressView.loadingContainer.visibility = View.GONE
        Toast.makeText(activity?.applicationContext, error, Toast.LENGTH_LONG).show()
    }


}
