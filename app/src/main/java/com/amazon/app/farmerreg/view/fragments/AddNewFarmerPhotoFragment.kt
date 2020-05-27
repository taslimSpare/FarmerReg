package com.amazon.app.farmerreg.view.fragments


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.amazon.app.farmerreg.R
import com.amazon.app.farmerreg.databinding.FragmentAddNewFarmerPhotoBinding
import com.amazon.app.farmerreg.helper.Constants
import com.amazon.app.farmerreg.helper.Utils
import com.amazon.app.farmerreg.view.viewmodel.FarmerVM
import com.bumptech.glide.Glide
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 */
class AddNewFarmerPhotoFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var fragmentAddNewFarmerPhotoBinding: FragmentAddNewFarmerPhotoBinding
    private lateinit var farmerVM: FarmerVM
    private lateinit var filePath: Uri
    private lateinit var bitmapFromUri: Bitmap
    private var GALLERYREQUESTCODE: Int = 9
    private var imageUrl: String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        navController = Navigation.findNavController(container!!)
        fragmentAddNewFarmerPhotoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_farmer_photo, container, false)
        farmerVM = ViewModelProviders.of(activity as FragmentActivity).get(FarmerVM::class.java)

        setObserver()


        // upload photo
        fragmentAddNewFarmerPhotoBinding.ibPhoto.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERYREQUESTCODE)
        }



        // button save
        fragmentAddNewFarmerPhotoBinding.btnFinish.setOnClickListener {

            if(imageUrl != "") {
                showProgressBar()
                farmerVM.saveFarmerDetails(farmerVM.farmerProfile)
            }

            else { Toast.makeText(requireActivity().applicationContext, "Upload a picture", Toast.LENGTH_LONG).show() }

        }

        return fragmentAddNewFarmerPhotoBinding.root
    }


    private fun setObserver() {

        farmerVM.farmerLivedata().observe(viewLifecycleOwner, Observer {

            when {
                it.contains(Constants.SAVE_USER_TO_ROOM_SUCCESSFUL) -> {
                    Toast.makeText(requireActivity().applicationContext, "Successful", Toast.LENGTH_LONG).show()
                    navController.navigate(R.id.action_addNewFarmerFragment_to_homeFragment)
                }
                it.contains(Constants.UPLOAD_USER_DETAILS_SUCCESSFUL) -> {
                    imageUrl = it.split(Constants.SEPARATOR)[1]
                    Glide
                        .with(requireActivity().applicationContext)
                        .load(bitmapFromUri)
                        .centerCrop()
                        .placeholder(R.drawable.farmer)
                        .into(fragmentAddNewFarmerPhotoBinding.ibPhoto)
                    hideProgressBar("")
                }
                it.contains(Constants.FAILED) -> {
                    hideProgressBar(it.split(Constants.SEPARATOR)[1])
                }
            }
        })
    }


    private fun showProgressBar() {
        fragmentAddNewFarmerPhotoBinding.ibPhoto.isEnabled = false
        fragmentAddNewFarmerPhotoBinding.ibBack.isEnabled = false
        fragmentAddNewFarmerPhotoBinding.btnFinish.isEnabled = false
        fragmentAddNewFarmerPhotoBinding.progressView.loadingContainer.visibility = View.VISIBLE
    }

    private fun hideProgressBar(error: String) {
        fragmentAddNewFarmerPhotoBinding.ibPhoto.isEnabled = true
        fragmentAddNewFarmerPhotoBinding.ibBack.isEnabled = true
        fragmentAddNewFarmerPhotoBinding.btnFinish.isEnabled = true
        fragmentAddNewFarmerPhotoBinding.progressView.loadingContainer.visibility = View.GONE
        if(error != "") Toast.makeText(activity?.applicationContext, error, Toast.LENGTH_LONG).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == GALLERYREQUESTCODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data!!
            try {
                bitmapFromUri = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, filePath)
                showProgressBar()
                farmerVM.uploadFarmerPictureToFirebaseStorage(filePath)
            }
            catch (e: IOException) {
                hideProgressBar(e.message.toString())
            }
        }

    }

}
