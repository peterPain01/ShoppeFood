package com.foodapp.view.main

import ApiService
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository

class driver_info : AppCompatActivity() {
    private lateinit  var backButton : ImageView
    private lateinit var homeButton : ConstraintLayout
    private lateinit var shipperName : TextView
    private val userService = UserRepository(SessionManager(this)).create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_driver_info)
    }

    override fun onStart() {
        super.onStart()
        backButton = findViewById(R.id.driver_info_back)
        homeButton = findViewById(R.id.driver_info_home)
        shipperName = findViewById(R.id.driver_info_name)
        initEvent()
        callApi()
    }

    private fun initEvent()
    {
        backButton.setOnClickListener{
            this.finish()
        }
        homeButton.setOnClickListener {
            val intent = Intent(this, MoneyMange::class.java)
            startActivity(intent)
        }
    }

    private fun callApi()
    {
        userService.
    }

}