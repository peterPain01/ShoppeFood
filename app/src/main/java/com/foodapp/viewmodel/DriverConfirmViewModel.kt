package com.foodapp.viewmodel

import ApiService
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Response

class DriverConfirmViewModel(val orderId: String, sessionManager: SessionManager): ViewModel() {
    private val userService = UserRepository(sessionManager).create(ApiService::class.java)

    fun cancelOrder(view: View) {
    }

    fun confirmOrder(view: View) {
        userService.confirmOrder(orderId).enqueue(object: retrofit2.Callback<ApiResult<Nothing>> {
            override fun onResponse(
                call: Call<ApiResult<Nothing>>,
                response: Response<ApiResult<Nothing>>
            ) {
                if (response.code() != 200) {
                    Log.e("FOODAPP:DriverConfirmViewModel", response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                Log.e("FOODAPP:DriverConfirmViewModel", t.message ?: "")
            }

        })
    }
}