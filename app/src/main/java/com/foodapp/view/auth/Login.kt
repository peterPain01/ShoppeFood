package com.foodapp.view.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import com.foodapp.databinding.ActivityLoginBinding
import com.foodapp.view.main.Homepage
import com.foodapp.view.main.seller_page
import com.foodapp.viewmodel.AuthViewModel

class Login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var authViewModel: AuthViewModel
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        userRepository = UserRepository()
        val sessionManager = SessionManager(this)
        authViewModel = AuthViewModel(sessionManager, userRepository)
        binding.loginViewModel = authViewModel
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        val context = this
        val errorMsg = findViewById<TextView>(R.id.errorMsg)
        val btnLogin = findViewById<AppCompatButton>(R.id.login_btnLogin)
        btnLogin.setOnClickListener{
            if (authViewModel.email == "user@gmail.com") {
                val intent = Intent(this, Homepage::class.java)
                startActivity(intent);
            }
            else if(authViewModel.email == "seller@gmail.com"){
                val intent = Intent(this, seller_page::class.java)
                startActivity(intent);
            }
//            authViewModel.login { isSuccess, Message ->
//                if(isSuccess)
//                {
//                    errorMsg.text = Message
//                }
//                else{
//                    errorMsg.text = Message
//                }
//            }
        }
    }

    fun onSignUpLinkClick(view: View) {
        val intent = Intent(this, Signup::class.java)
        startActivity(intent)
    }

}
