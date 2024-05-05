package com.foodapp.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.ActivityCreateShipperBinding
import com.foodapp.helper.helper
import com.foodapp.view.auth.Login
import com.foodapp.viewmodel.CreateShipperViewModel
import java.io.File

class CreateShipper : AppCompatActivity() {
    private val PICK_AVATAR_REQ_CODE: Int = 100
    private val PICK_VEHICLE_REQ_CODE: Int = 101
    private lateinit var binding: ActivityCreateShipperBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_shipper)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_shipper)
        binding.lifecycleOwner = this
        binding.viewModel = CreateShipperViewModel(SessionManager(this))
    }

    override fun onStart() {
        super.onStart()
        binding.activityCreateShipperBackBtn.setOnClickListener { this.finish() }
        binding.activityCreateShipperSummit.setOnClickListener {
            if (binding.viewModel?.validate() ?: false) {
                binding.viewModel?.createShipper() {
                    helper.displayPopup(this, "Success", helper.PopupType.Info) {
                        val newIntent = Intent(this, Login::class.java)
                        startActivity(newIntent)
                    }
                }
            } else {
                helper.displayPopup(this, "Please fill in all missing fields!", helper.PopupType.Error) {}
            }
        }
        binding.activityCreateShipperUploadAvatarBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_AVATAR_REQ_CODE)
        }
        binding.activityCreateShipperUploadVehicleBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_VEHICLE_REQ_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                when (requestCode) {
                    PICK_AVATAR_REQ_CODE -> {
                        binding.viewModel!!.avatarPath = getRealPathFromUri(it)
                        binding.activityCreateShipperUploadAvatarImage.setImageURI(it)
                    }

                    PICK_VEHICLE_REQ_CODE -> {
                        binding.viewModel!!.vehiclePath = getRealPathFromUri(it)
                        binding.activityCreateShipperUploadVehicleImage.setImageURI(it)
                    }
                }
            }
        }
    }

    private fun getRealPathFromUri(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        cursor?.moveToFirst()
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        val path = cursor?.getString(columnIndex ?: 0)
        cursor?.close()
        return path ?: ""
    }
}