package com.foodapp.viewmodel

import ApiService
import androidx.lifecycle.ViewModel
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.MapPosition
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Response

class DriverHomeViewModel(sessionManager: SessionManager, val displayMsg: (String) -> Unit): ViewModel() {
    val userService = UserRepository(sessionManager).create(ApiService::class.java)

    fun updateLocation(location: MapPosition) {
        userService.updateAddress(location).enqueue(object: retrofit2.Callback<ApiResult<Nothing>>{
            override fun onResponse(
                call: Call<ApiResult<Nothing>>,
                response: Response<ApiResult<Nothing>>
            ) {
                if (response.code() != 200) {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                displayMsg(t.message ?: "")
            }
        })
    }

    fun updateToken(token: String) {
        userService.updateToken(token).enqueue(object: retrofit2.Callback<ApiResult<Nothing>>{
            override fun onResponse(
                call: Call<ApiResult<Nothing>>,
                response: Response<ApiResult<Nothing>>
            ) {
                if (response.code() != 200) {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                displayMsg(t.message ?: "")
            }
        })
    }
}