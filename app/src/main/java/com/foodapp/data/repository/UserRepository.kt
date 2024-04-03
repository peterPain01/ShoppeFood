package com.foodapp.data.repository

import ApiService
import retrofit2.Call
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.foodapp.data.model.User
import okhttp3.ResponseBody

class UserRepository {
    private val authService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)

    fun logIn(user : User) : Call<User>  {
        return authService.logIn(user)
    }
    fun signUp(user: User): Call<User> {
        return authService.signUp(user)
    }
}

