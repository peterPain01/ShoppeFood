package com.foodapp.view.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.ActivityLoginBinding
import com.foodapp.view.main.Admin_Page
import com.foodapp.view.main.Homepage
import com.foodapp.view.main.driver_home
import com.foodapp.view.main.seller_page
import com.foodapp.viewmodel.AuthViewModel

class Login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val sessionManager = SessionManager(this)
        authViewModel = AuthViewModel(sessionManager)
        binding.loginViewModel = authViewModel
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        val errorMsg = findViewById<TextView>(R.id.errorMsg)
        val btnLogin = findViewById<AppCompatButton>(R.id.login_btnLogin)
        btnLogin.setOnClickListener{
            authViewModel.login { isSuccess, user, Message ->
                if(isSuccess)
                {
                    var klazz: Class<*>?
                    when (user?.role) {
                        "user" -> klazz = Homepage::class.java
                        "shop" -> klazz = seller_page::class.java
                        "admin" -> klazz = Admin_Page::class.java
                        "shipper" -> klazz = driver_home::class.java
                        else -> {
                            Log.e("FoodApp:main/Login", "Unknown role: " + user?.role)
                            throw Exception("Unknown role: " + user?.role)
                        }
                    }
                    klazz.let {
                        val new_intent = Intent(this, klazz)
                        startActivity(new_intent)
                    }
                }
                else{
                    errorMsg.text = Message
                }
            }
        }
    }

    fun onSignUpLinkClick(view: View) {
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
    }

}
