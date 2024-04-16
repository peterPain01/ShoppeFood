package com.foodapp.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.foodapp.R

class EditUserInfo : AppCompatActivity() {
    private lateinit var back_btn: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_user_info)
    }

    override fun onStart() {
        super.onStart()
        back_btn = findViewById<ImageButton>(R.id.activity_edit_user_info_back_btn)
        back_btn.setOnClickListener {
            this.finish()
        }
    }
}