package com.foodapp.viewmodel

import ApiService
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.User
import com.foodapp.data.model.UserAddress
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import com.foodapp.view.adapter.AddressAdapter
import com.foodapp.view.main.ManageAddress
import retrofit2.Call
import retrofit2.Response

class UserAddressViewModel(sessionManager: SessionManager, val displayMsg: (String?) -> Unit): ViewModel() {
    private val userService = UserRepository(sessionManager).create(ApiService::class.java)

    fun updateUser(user: User) {
        userService.upadteUser(user).enqueue(object: retrofit2.Callback<ApiResult<Nothing>> {
            override fun onResponse(
                call: Call<ApiResult<Nothing>>,
                response: Response<ApiResult<Nothing>>
            ) {
                if (response.code() != 200) {
                    displayMsg(response.body()?.message)
                }
            }

            override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                displayMsg(t.message)
            }
        })
    }
}