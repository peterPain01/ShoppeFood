package com.foodapp.view.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.data.repository.UserRepository
import com.foodapp.databinding.ActivitySignupBinding
import com.foodapp.viewmodel.AuthViewModel

class Signup : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    lateinit var authViewModel: AuthViewModel
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        userRepository = UserRepository()
        authViewModel = AuthViewModel(userRepository)
        binding.signupViewModel = authViewModel
        binding.lifecycleOwner = this

    }
    override fun onStart() {
        super.onStart()

        val context = this
        val errorMsg = findViewById<TextView>(R.id.errorMsg)
        val btnSignup = findViewById<AppCompatButton>(R.id.signup_btnSignup)
        btnSignup.setOnClickListener {
            authViewModel.signUp { isSuccess, Message ->
                if(isSuccess)
                {
                    finish()
                }
                else{
                    errorMsg.text = Message
                }
            }
        }
    }
}