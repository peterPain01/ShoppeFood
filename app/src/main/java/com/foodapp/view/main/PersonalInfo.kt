package com.foodapp.view.main

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.foodapp.R

class PersonalInfo : AppCompatActivity() {
    private lateinit var edit_btn: Button;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)
        edit_btn = findViewById<Button>(R.id.activity_personal_info_edit_button)
        edit_btn.paintFlags = edit_btn.paintFlags.or(Paint.UNDERLINE_TEXT_FLAG)
    }

    fun onClickedBack(view: View) {
        finish()
    }
}