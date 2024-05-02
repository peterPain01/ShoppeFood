package com.foodapp.view.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.ActivityUserInfoBinding
import com.foodapp.view.auth.Login
import com.foodapp.viewmodel.UserInfoViewModel

class UserInfo : AppCompatActivity() {
    private lateinit var binding: ActivityUserInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)
        binding.lifecycleOwner = this
        binding.viewModel = UserInfoViewModel(SessionManager(this)){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        binding.viewModel?.user?.observe(this) {
            Glide.with(this)
                .load(it)
                .into(binding.activityUserInfoImage)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.activityUserInfoBackBtn.setOnClickListener {
            this.finish()
        }
        binding.activityUserInfoAddressContainer.setOnClickListener {
            val new_intent = Intent(this, UserAddress::class.java);
            new_intent.putExtra("info", binding.viewModel?.user?.value)
            startActivity(new_intent);
        }
        binding.activityUserInfoPersonalInfo.setOnClickListener {
            val new_intent = Intent(this, PersonalInfo::class.java);
            new_intent.putExtra("info", binding.viewModel?.user?.value)
            startActivity(new_intent);
        }
        binding.activityUserInfoLogout.setOnClickListener {
            binding.viewModel!!.logout {
                SessionManager(this).removeAuthToken()
                val new_intent = Intent(this, Login::class.java)
                startActivity(new_intent)
            }
        }
    }
}