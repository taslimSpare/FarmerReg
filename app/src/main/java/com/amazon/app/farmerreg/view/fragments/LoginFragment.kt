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
import com.amazon.app.farmerreg.databinding.FragmentLoginBinding
import com.amazon.app.farmerreg.helper.Constants
import com.amazon.app.farmerreg.helper.Utils
import com.amazon.app.farmerreg.view.ExitActivity
import com.amazon.app.farmerreg.view.viewmodel.AuthVM

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var fragmentLoginBinding: FragmentLoginBinding
    private lateinit var authVM: AuthVM


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        navController = Navigation.findNavController(container!!)
        authVM = ViewModelProviders.of(activity as FragmentActivity).get(AuthVM::class.java)
        fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)


        // onBackPressed
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                ExitActivity().exit(activity!!.applicationContext)
            }
        })


        // when sign up button is clicked
        fragmentLoginBinding.switchToSignUp.setOnClickListener { navController.navigate(R.id.action_loginFragment_to_signupFragment) }


        // when done/sign-in button is clicked
        fragmentLoginBinding.btnSignIn.setOnClickListener {
            if(!(Utils().errorfy(fragmentLoginBinding.etEmail, fragmentLoginBinding.etPassword))) {
                showProgressBar()
                authVM.logIn(fragmentLoginBinding.etEmail.text.toString().trim(), fragmentLoginBinding.etPassword.text.toString().trim())
                observe()
            }
        }

        return fragmentLoginBinding.root
    }



    private fun observe() {
        authVM.authLiveData().observe(viewLifecycleOwner, Observer {

            if(it.contains(Constants.SAVE_USER_TO_ROOM_SUCCESSFUL)) {
                navController.navigate(R.id.action_loginFragment_to_homeFragment)
            }
            else if(it.contains(Constants.FAILED)) {
                hideProgressBar(it.split(Constants.SEPARATOR)[1])
            }
        })
    }




    private fun showProgressBar() {
        fragmentLoginBinding.etEmail.isEnabled = false
        fragmentLoginBinding.etPassword.isEnabled = false
        fragmentLoginBinding.progressView.loadingContainer.visibility = View.VISIBLE
    }

    private fun hideProgressBar(error: String) {
        fragmentLoginBinding.etEmail.isEnabled = true
        fragmentLoginBinding.etPassword.isEnabled = true
        fragmentLoginBinding.progressView.loadingContainer.visibility = View.GONE
        Toast.makeText(activity?.applicationContext, error, Toast.LENGTH_LONG).show()
    }

}
