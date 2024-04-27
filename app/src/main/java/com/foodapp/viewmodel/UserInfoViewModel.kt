package com.foodapp.viewmodel

import ApiService
import androidx.lifecycle.MutableLiveData
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.User
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Response

class UserInfoViewModel(val displayMsg: (String?) -> Unit, showImage: (String?) -> Unit, val sessionManager: SessionManager) {
    private val userService = UserRepository(sessionManager).create(ApiService::class.java)
    var user: MutableLiveData<User?> = MutableLiveData()
    init {
        userService.getCurrentUserInfo().enqueue(object: retrofit2.Callback<ApiResult<User>> {
            override fun onResponse(
                call: Call<ApiResult<User>>,
                response: Response<ApiResult<User>>
            ) {
                val body = response.body()
                if (response.code() == 200) {
                    user.value = body?.metadata
                    showImage(user.value?.avatar)
                } else {
                    displayMsg(body?.message)
                }
            }

            override fun onFailure(call: Call<ApiResult<User>>, t: Throwable) {
                displayMsg(t.message)
            }
        })
    }

    fun logout(done: () -> Unit) {
        userService.logout().enqueue(object: retrofit2.Callback<ApiResult<Nothing>> {
            override fun onResponse(
                call: Call<ApiResult<Nothing>>,
                response: Response<ApiResult<Nothing>>
            ) {
                val body = response.body()!!
                if (response.code() == 200) {
                    done()
                    // sessionManager.removeAuthToken()
                } else {
                    displayMsg(body.message)
                }
            }

            override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                displayMsg(t.message)
            }
        })
    }
}