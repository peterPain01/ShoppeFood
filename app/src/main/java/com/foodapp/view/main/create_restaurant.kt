package com.foodapp.view.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
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
import androidx.appcompat.app.AppCompatActivity
import com.foodapp.R
import com.foodapp.data.model.MapPosition
import com.foodapp.data.model.Position
import com.foodapp.data.model.Shop
import com.foodapp.view.adapter.MultipleChoiceSpinnerAdapter
import com.foodapp.viewmodel.AdminViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import java.sql.Time


class create_restaurant : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST_CODE = 100
    private var url : URL ?= null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_restaurant)

        val btn = findViewById<Button>(R.id.create_restaurant_LoadImage)
        val choices = listOf("Choice 1", "Choice 2", "Choice 3", "Choice 4", "Choice 5")
        val spinner = findViewById<Spinner>(R.id.create_restaurant_spinner)
        val adapter = MultipleChoiceSpinnerAdapter(this, choices)
        val back = findViewById<ImageButton>(R.id.create_restaurant_imageButton2)
        val btnSubmit = findViewById<Button>(R.id.create_restaurant_track_btn);

        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
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
        back.setOnClickListener {
            finish();
        }
        btnSubmit.setOnClickListener {
            var a = Shop(
                id = "",
                name = findViewById<EditText>(R.id.create_restaurant_EditText10).text.toString(),
                image = url.toString(),
                avg_rating = 4.0,
                position = Position(findViewById<EditText>(R.id.create_restaurant_EditText12).text.toString(), MapPosition(0.0, 0.0)),
                openHour = Time((1 + 9) * 3600000L),
                closeHour = Time((1 + 18) * 3600000L),
                description = findViewById<EditText>(R.id.create_restaurant_EditText11).text.toString(),
                phone = "0123456789",
                category = listOf(),
                status = "active",
            )
            Log.i("Vushop", a.toString());
           // AdminViewModel(this).CreateShop(a);
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
                try {
                    url = URL(selectedImageUri.toString())

                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                }
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