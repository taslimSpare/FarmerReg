@file:Suppress("DEPRECATION")

package com.amazon.app.farmerreg.view.fragments



import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.amazon.app.farmerreg.R
import com.amazon.app.farmerreg.view.viewmodel.AuthVM

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var authVM: AuthVM


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        navController = Navigation.findNavController(container!!)
        authVM = ViewModelProviders.of(activity as FragmentActivity).get(AuthVM::class.java)


        // onBackPressed
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // do nothing
            }
        })


        // show splash screen for two seconds before proceeding to relevant fragment
        object : CountDownTimer(2000, 1000) {

            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                if(authVM.isUserLoggedIn()) {
                    navController.navigate(R.id.action_splashFragment_to_homeFragment)
                }
                else {
                    navController.navigate(R.id.action_splashFragment_to_loginFragment)
                }
            }
        }.start()


        return inflater.inflate(R.layout.fragment_splash, container, false)

    }


}
