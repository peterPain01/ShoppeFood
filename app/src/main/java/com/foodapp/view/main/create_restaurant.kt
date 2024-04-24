package com.foodapp.view.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.foodapp.R
import com.foodapp.view.adapter.MultipleChoiceSpinnerAdapter
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
class create_restaurant : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_restaurant)

        val btn = findViewById<Button>(R.id.create_restaurant_LoadImage)
        val choices = listOf("Choice 1", "Choice 2", "Choice 3", "Choice 4", "Choice 5")
        val spinner = findViewById<Spinner>(R.id.create_restaurant_spinner)
        val adapter = MultipleChoiceSpinnerAdapter(this, choices)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                adapter.toggleSelection(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

//        btnShowSelection.setOnClickListener {
//            val selectedItems = adapter.getSelectedItems()
//            val selectedItemsText = selectedItems.map { items[it] }.joinToString(", ")
//            Toast.makeText(this, "Selected Items: $selectedItemsText", Toast.LENGTH_SHORT).show()
//        }

        btn.setOnClickListener {
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
                // handle send form data
                val imageView = findViewById<ImageView>(R.id.create_restaurant_BackGroundFood)
                val file = File(selectedImageUri?.let { getRealPathFromUri(it) }) // Convert Uri to File
                val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

                imageView?.setImageURI(selectedImageUri);
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