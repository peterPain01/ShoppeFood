package com.foodapp.data.repository

import ApiService
import retrofit2.Call
import com.foodapp.data.model.User
import com.foodapp.data.model.auth.AuthResponse

class UserRepository {
    private val authService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)

    fun logIn(user : User) : Call<AuthResponse>  {
        return authService.logIn(user)
    }
    fun signUp(user: User): Call<AuthResponse> {
        return authService.signUp(user)
    }
}

