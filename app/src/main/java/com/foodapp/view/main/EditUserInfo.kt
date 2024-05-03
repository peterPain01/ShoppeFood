package com.foodapp.view.main

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.data.model.User
import com.foodapp.databinding.ActivityEditUserInfoBinding
import com.foodapp.helper.helper
import com.foodapp.viewmodel.InfoViewModel
import java.io.File
import java.net.URL

class EditUserInfo : AppCompatActivity() {
    private lateinit var binding: ActivityEditUserInfoBinding
    private lateinit var back_btn: ImageButton
    private lateinit var upload_btn: ImageButton
    private lateinit var save_btn: androidx.cardview.widget.CardView
    private val PICK_IMAGE_REQUEST_CODE = 100
    private var file: File ?= null;
    private var user: User ?= null;
    private var infoViewModel: InfoViewModel ?= null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_info)

        user = intent.getSerializableExtra("info", User::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_user_info)
        helper.ShowImageUrl(user?.avatar, findViewById<ImageView>(R.id.edit_user_info_avatar))
        binding.lifecycleOwner = this
        infoViewModel =  user?.let { InfoViewModel(it) }
        binding.infoViewModel = infoViewModel
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
                        val imageView: ImageView = findViewById(R.id.edit_user_info_avatar)
                        imageView.setImageURI(selectedImageUri)
                    }
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        back_btn = findViewById<ImageButton>(R.id.activity_edit_user_info_back_btn)
        upload_btn = findViewById<ImageButton>(R.id.edit_user_info_btn)
        save_btn = findViewById<androidx.cardview.widget.CardView>(R.id.edit_user_info_save)
        back_btn.setOnClickListener {
            this.finish()
        }
        upload_btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        }
        save_btn.setOnClickListener {
            file?.let { it1 -> infoViewModel?.uploadFile(it1) }
        }
    }
}