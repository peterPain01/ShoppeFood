package com.foodapp.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import com.foodapp.R
import com.foodapp.view.auth.Login

class UserInfo : AppCompatActivity() {
    private lateinit var back_btn: ImageButton
    private lateinit var addr_container: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
    }

    override fun onStart() {
        super.onStart()
        back_btn = findViewById<ImageButton>(R.id.activity_user_info_back_btn)
        back_btn.setOnClickListener {
            this.finish()
        }
        addr_container = findViewById<View>(R.id.activity_user_info_address_container)
        addr_container.setOnClickListener {
            val new_intent = Intent(this, UserAddress::class.java);
            startActivity(new_intent);
        }
    }

    fun onClickedPersonalInfo(view: View) {
        val new_intent = Intent(this, PersonalInfo::class.java);
        startActivity(new_intent);
    }

    fun onLogOutClicked(view: View) {
        val new_intent = Intent(this, Login::class.java)
        startActivity(new_intent)
    }
}