package com.foodapp.view.main

import ApiService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Shop
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.data.repository.UserRepository
import com.foodapp.databinding.ActivityAllShopBinding
import com.foodapp.view.adapter.VerticalAdapter
import com.foodapp.view.adapter.VerticalShopViewHolder
import retrofit2.Call
import retrofit2.Response

class AllShop : AppCompatActivity() {
    private lateinit var binding: ActivityAllShopBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_shop)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_shop)
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        binding.activityAllShopBackBtn.setOnClickListener { this.finish() }
        val sessionManager = SessionManager(this)
        val service = if (sessionManager.isLogin()) UserRepository(sessionManager).create(ApiService::class.java) else RetrofitClient.retrofit.create(ApiService::class.java)
        service.getAllShops().enqueue(object: retrofit2.Callback<ApiResult<List<Shop>>> {
            override fun onResponse(
                call: Call<ApiResult<List<Shop>>>,
                response: Response<ApiResult<List<Shop>>>
            ) {
                if (response.isSuccessful) {
                    val shops = response.body()?.metadata ?: listOf()
                    binding.activityAllShopList.adapter = VerticalAdapter(shops, R.layout.item_vertical, VerticalShopViewHolder::class.java)
                } else {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<List<Shop>>>, t: Throwable) {
                displayMsg(t.stackTraceToString())
            }
        })
    }
    fun displayMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        Log.d("FOODAPP:driver_info", msg)
    }
}