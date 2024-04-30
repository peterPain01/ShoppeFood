package com.foodapp.view.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.foodapp.R
import com.foodapp.view.auth.Login

class Onboarding_02 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding02)

        val next = findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.onboarding_02_appCompatButton)
        val skip = findViewById<Button>(R.id.onboarding_02_button)

        next.setOnClickListener{
            val new_intent = Intent(this, onboarding_03::class.java)
            startActivity(new_intent)
        }

        skip.setOnClickListener {
            val new_intent = Intent(this, Login::class.java)
            startActivity(new_intent)
        }
    }
}