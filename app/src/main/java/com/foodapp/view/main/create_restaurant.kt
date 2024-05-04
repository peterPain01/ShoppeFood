package com.foodapp.view.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.data.model.Shop
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.ActivityCreateRestaurantBinding
import com.foodapp.view.adapter.AddressAdapter
import com.foodapp.view.adapter.MultipleChoiceSpinnerAdapter
import com.foodapp.viewmodel.AdminViewModel
import com.foodapp.viewmodel.UserAddressViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import java.sql.Time


class create_restaurant : AppCompatActivity() {
    private lateinit var binding: ActivityCreateRestaurantBinding
    private val PICK_IMAGE_REQUEST_CODE = 100
    private var file: File ?= null;
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    var address : com.foodapp.data.model.UserAddress ?= null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_restaurant)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_restaurant)
        binding.lifecycleOwner = this
        val btn = findViewById<Button>(R.id.create_restaurant_LoadImage)

        val spinner = findViewById<Spinner>(R.id.create_restaurant_spinner)
        val back = findViewById<ImageButton>(R.id.create_restaurant_imageButton2)
        val btnSubmit = findViewById<Button>(R.id.create_restaurant_track_btn);
        val btnAddress = findViewById<TextView>(R.id.create_restaurant_EditText12)

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == UserAddress.ADD_CODE) {
                val data: Intent? = result.data
                address = data?.getSerializableExtra("address", com.foodapp.data.model.UserAddress::class.java)
                address?.let {
                    btnAddress.text = address!!.street
                }
            }
        }

        val adminViewModel = AdminViewModel(this, spinner);
        binding.viewModel = adminViewModel;

        btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        }
        back.setOnClickListener {
            finish();
        }
        btnSubmit.setOnClickListener {
                file?.let { it1 ->
                    address?.let { it2 ->
                        adminViewModel.CreateShop("012345678", "07:00", "10:00", it2,
                            it1)
                    }
                };
        }
        btnAddress.setOnClickListener {
            val new_intent = Intent(this, ManageAddress::class.java);
            resultLauncher.launch(new_intent);
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST_CODE) {
                val selectedImageUri = data?.data
                // handle send form data
                selectedImageUri?.let {
                    val realPath = getRealPathFromUri(it)
                    realPath?.let { path ->
                        file = File(path)


                        val imageView: ImageView = findViewById(R.id.create_restaurant_BackGroundFood)
                        imageView.setImageURI(selectedImageUri)
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
