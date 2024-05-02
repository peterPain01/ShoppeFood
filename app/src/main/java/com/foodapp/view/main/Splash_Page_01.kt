package com.foodapp.view.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.foodapp.R
import com.foodapp.view.auth.Login

class Splash_Page_01 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_page01)

        val next = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.onboarding_01_appCompatButton)
        val skip = findViewById<Button>(R.id.onboarding_01_button)

        next.setOnClickListener{
            val new_intent = Intent(this, Onboarding_02::class.java)
            startActivity(new_intent)
        }

        skip.setOnClickListener {
            val new_intent = Intent(this, Login::class.java)
            startActivity(new_intent)
            overridePendingTransition(R.drawable.slide_in_right, R.drawable.slide_out_left);
        }
    }
}