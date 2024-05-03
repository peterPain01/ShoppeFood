package com.foodapp.viewmodel

import ApiService
import android.util.Log
import androidx.lifecycle.ViewModel
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Product
import com.foodapp.data.model.User
import com.foodapp.data.repository.RetrofitClient
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class CreateProductViewModel(products: Product): ViewModel()  {
    var product: Product = products;
    var service = RetrofitClient.retrofit.create(ApiService::class.java)
    fun createShop(image: File) {


        val requestFile = image.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("image", image.name, requestFile)

//        service.createProduct().enqueue(object :
//            Callback<ApiResult<Product>> {
//            override fun onResponse(
//                call: Call<ApiResult<Product>>,
//                response: Response<ApiResult<Product>>
//            ) {
//                if (response.isSuccessful) {
//                    val user = response.body()
//
//                } else {
//                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
//                    Log.e("CreateShop", "Error creating shop: $errorMessage")
//                }
//            }
//
//            override fun onFailure(
//                call: Call<ApiResult<Product>>,
//                t: Throwable
//            ) {
//                Log.e("CreateShop", "Error: ${t.message ?: "Unknown error"}")
//            }
//        })
    }
}