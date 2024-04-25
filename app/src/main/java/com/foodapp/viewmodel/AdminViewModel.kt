package com.foodapp.viewmodel

import ApiService
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Category
import com.foodapp.data.model.Shop
import com.foodapp.data.model.auth.AuthResponse
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.data.repository.UserRepository
import com.foodapp.view.adapter.HorizontalAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class AdminViewModel (private val context: Context){
    private val service = UserRepository(SessionManager(context)).create(ApiService::class.java)
    fun CreateShop(shop: Shop) {
        service.createShop(shop).enqueue(object : Callback<ApiResult<Shop>> {
            override fun onResponse(
                call: Call<ApiResult<Shop>>,
                response: Response<ApiResult<Shop>>
            ) {
                if (response.isSuccessful) {
                    val shopResult = response.body()
                    Log.i("shopResult", shopResult.toString());
                    if (shopResult != null) {
                        Toast.makeText(context, "Shop created successfully!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Shop creation succeeded but no data received!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Log.e("CreateShop", "Error creating shop: $errorMessage")
                    Toast.makeText(context, "Failed to create shop: $errorMessage", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(
                call: Call<ApiResult<Shop>>,
                t: Throwable
            ) {
                Log.e("CreateShop", "Error: ${t.message ?: "Unknown error"}")
                Toast.makeText(context, t.message ?: "Unknown error", Toast.LENGTH_LONG).show()
            }
        })
    }
}