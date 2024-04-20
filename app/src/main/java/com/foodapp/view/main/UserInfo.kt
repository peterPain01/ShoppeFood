package com.foodapp.view.main

import ApiService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.User
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.data.repository.UserRepository
import com.foodapp.databinding.ActivityUserInfoBinding
import com.foodapp.view.auth.Login
import retrofit2.Call
import retrofit2.Response

class UserInfo : AppCompatActivity() {
    private lateinit var service: ApiService
    private lateinit var binding: ActivityUserInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_info)
    }

    override fun onStart() {
        super.onStart()
        service = UserRepository(SessionManager(this)).create(ApiService::class.java)
        val context = this
        service!!.getCurrentUserInfo().enqueue(object: retrofit2.Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.d("TUONG", response.toString())
                if (response.code() == 200) {
                    val body = response.body()
                    if (body != null) {
                        binding.info = body
                        Glide.with(context)
                            .load(body.avatar)
                            .into(binding.activityUserInfoImage)
                    } else {
                        Log.e("API", "[user] Missing body")
                        Toast.makeText(null, "Server not working", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(null, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Toast.makeText(null, t.message, Toast.LENGTH_SHORT).show()
            }
        })
        binding.activityUserInfoBackBtn.setOnClickListener {
            this.finish()
        }
        binding.activityUserInfoAddressContainer.setOnClickListener {
            val new_intent = Intent(this, UserAddress::class.java);
            new_intent.putExtra("info", binding.info)
            startActivity(new_intent);
        }
        binding.activityUserInfoPersonalInfo.setOnClickListener {
            val new_intent = Intent(this, PersonalInfo::class.java);
            startActivity(new_intent);
        }
        binding.activityUserInfoLogout.setOnClickListener {
            val new_intent = Intent(this, Login::class.java)
            startActivity(new_intent)
        }
    }
}