package com.foodapp.view.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.foodapp.R
import com.foodapp.view.auth.Login

class onboarding_03 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding03)

        val next = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.onboarding_03_appCompatButton)

        next.setOnClickListener{
            val new_intent = Intent(this, Login::class.java)
            startActivity(new_intent)
            overridePendingTransition(R.drawable.slide_in_right, R.drawable.slide_out_left);
        }
    }
}