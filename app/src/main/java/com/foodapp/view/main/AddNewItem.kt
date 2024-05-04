package com.foodapp.view.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.ActivityAddNewItemsBinding
import com.foodapp.databinding.ActivityCreateRestaurantBinding
import com.foodapp.helper.helper
import com.foodapp.viewmodel.CreateProductViewModel
import java.io.File


class AddNewItem : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewItemsBinding
    private val PICK_IMAGE_REQUEST_CODE = 100
    private var file: File ?= null;
    private var createViewModel: CreateProductViewModel ?= null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_items)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_items)
        binding.lifecycleOwner = this

        val spinner = findViewById<Spinner>(com.foodapp.R.id.add_new_items_spinner)
        createViewModel = CreateProductViewModel(this, spinner, SessionManager(this))
        binding.viewModel = createViewModel;
    }

    override fun onStart() {
        super.onStart()
        val imageView: ImageView = findViewById(R.id.add_new_items_upload)
        val submit: RelativeLayout = findViewById(R.id.add_new_items_submit)
        val back: ImageView = findViewById(R.id.add_new_items_back)

        back.setOnClickListener {
            finish()
        }

        submit.setOnClickListener {
            file?.let { it1 -> createViewModel?.createProduct(it1) };
            helper.displayPopup(this, "Success", helper.PopupType.Info) {
                finish()
            }
        }

        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST_CODE) {
                val selectedImageUri = data?.data
                selectedImageUri?.let {
                    val realPath = getRealPathFromUri(it)
                    realPath?.let { path ->
                        file = File(path)
                        val imageView: ImageView = findViewById(R.id.add_new_items_upload)
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