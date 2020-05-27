package com.amazon.app.farmerreg.view.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.amazon.app.farmerreg.R
import com.amazon.app.farmerreg.databinding.FragmentAllFarmersBinding
import com.amazon.app.farmerreg.model.pojo.FarmerProfile
import com.amazon.app.farmerreg.view.FarmersAdapter
import com.amazon.app.farmerreg.view.viewmodel.FarmerVM

/**
 * A simple [Fragment] subclass.
 */
class AllFarmersFragment : Fragment(), FarmersAdapter.ItemClickListener {

    private lateinit var navController: NavController
    private lateinit var fragmentAllFarmersBinding: FragmentAllFarmersBinding
    private lateinit var farmerVM: FarmerVM
    private lateinit var myFarmers: List<FarmerProfile>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        navController = Navigation.findNavController(container!!)
        fragmentAllFarmersBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_farmers, container, false)
        farmerVM = ViewModelProviders.of(activity as FragmentActivity).get(FarmerVM::class.java)

        observe()

        farmerVM.downloadAllFarmsFromRoom()

        return fragmentAllFarmersBinding.root

    }


    private fun observe() {
        farmerVM.allFarmersLivedata().observe(viewLifecycleOwner, Observer {
            if(it.isEmpty()) {
                fragmentAllFarmersBinding.rvAllFarmers.visibility = View.GONE
                fragmentAllFarmersBinding.clNoFarmer.visibility = View.VISIBLE
            }

            else {
                fragmentAllFarmersBinding.clNoFarmer.visibility = View.GONE

                myFarmers = it
                val farmersAdapter: FarmersAdapter = FarmersAdapter(requireActivity().applicationContext, myFarmers)
                farmersAdapter.setClickListener(this)

                fragmentAllFarmersBinding.rvAllFarmers.visibility = View.VISIBLE
                fragmentAllFarmersBinding.rvAllFarmers.layoutManager = GridLayoutManager(requireActivity(), 2)
                fragmentAllFarmersBinding.rvAllFarmers.adapter = farmersAdapter



            }
        })
    }


    override fun onItemClick(view: View, position: Int) {
        farmerVM.highlightedFarmer = myFarmers[position]
        navController.navigate(R.id.action_allFarmersFragment_to_farmerProfileFragment)
    }


}
