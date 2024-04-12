package com.foodapp.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.foodapp.R

class UserInfo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
    }

    fun onClickedPersonalInfo(view: View) {
        val new_intent = Intent(this, PersonalInfo::class.java);
        startActivity(new_intent);
    }
}