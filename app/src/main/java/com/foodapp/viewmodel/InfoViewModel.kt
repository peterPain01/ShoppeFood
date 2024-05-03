package com.foodapp.viewmodel

import ApiService
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Shop
import com.foodapp.data.model.User
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class InfoViewModel(users: User): ViewModel()  {
    var user: User = users;
    var service = RetrofitClient.retrofit.create(ApiService::class.java)
    fun uploadFile(image: File) {
        val Name = user.fullname?.toRequestBody("text/plain".toMediaTypeOrNull());
        val Phone = user.phone?.toRequestBody("text/plain".toMediaTypeOrNull());
        val email = user.email?.toRequestBody("text/plain".toMediaTypeOrNull());
        val bio = user.bio?.toRequestBody("text/plain".toMediaTypeOrNull())

        val requestFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", image.name, requestFile)

        if (Name != null && email != null && Phone != null && bio != null) {
            service.updateUser(Name, email, Phone, bio, imagePart).enqueue(object : Callback<ApiResult<User>> {
                override fun onResponse(
                    call: Call<ApiResult<User>>,
                    response: Response<ApiResult<User>>
                ) {
                    if (response.isSuccessful) {
                        val user = response.body()

                    } else {
                        val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                        Log.e("CreateShop", "Error creating shop: $errorMessage")
                    }
                }

                override fun onFailure(
                    call: Call<ApiResult<User>>,
                    t: Throwable
                ) {
                    Log.e("CreateShop", "Error: ${t.message ?: "Unknown error"}")
                }
            })
        }
    }
}