package com.foodapp.view.main

import ApiService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Shipper
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import com.foodapp.databinding.ActivityDriverInfoBinding
import com.foodapp.view.auth.Login
import retrofit2.Call
import retrofit2.Response

class driver_info : AppCompatActivity() {
    private lateinit var binding: ActivityDriverInfoBinding
    private lateinit var userService: ApiService
    private var fullname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_driver_info)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_driver_info)
        binding.lifecycleOwner = this
        userService = UserRepository(SessionManager(this)).create(ApiService::class.java)
    }

    override fun onStart() {
        super.onStart()
        initEvent()
        callApi()
    }

    private fun initEvent()
    {
        binding.driverInfoBack.setOnClickListener{
            this.finish()
        }
        binding.driverInfoHome.setOnClickListener {
            val intent = Intent(this, MoneyMange::class.java)
            intent.putExtra("fullname", fullname)
            startActivity(intent)
        }
        binding.activityDriverInfoLogout.setOnClickListener {
            logout {
                SessionManager(this).removeAuthToken()
                val new_intent = Intent(this, Login::class.java)
                startActivity(new_intent)
            }
        }
    }

    private fun callApi()
    {
        val context = this
        userService.getShipperInfo().enqueue(object: retrofit2.Callback<ApiResult<Shipper>> {
            override fun onResponse(
                call: Call<ApiResult<Shipper>>,
                response: Response<ApiResult<Shipper>>
            ) {
                if (response.isSuccessful) {
                    val shipper = response.body()?.metadata!!
                    fullname = shipper.fullname
                    binding.driverInfoName.text = shipper.fullname
                    Glide.with(context)
                        .load(shipper.avatar)
                        .into(binding.driverInfoItemReviewImg)
                } else {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<Shipper>>, t: Throwable) {
                displayMsg(t.stackTraceToString())
            }
        })
    }
    fun logout(done: () -> Unit) {
        userService.logout().enqueue(object: retrofit2.Callback<ApiResult<Nothing>> {
            override fun onResponse(
                call: Call<ApiResult<Nothing>>,
                response: Response<ApiResult<Nothing>>
            ) {
                if (response.isSuccessful) {
                    done()
                } else {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                displayMsg(t.stackTraceToString())
            }
        })
    }
    fun displayMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        Log.d("FOODAPP:driver_info", msg)
    }

}