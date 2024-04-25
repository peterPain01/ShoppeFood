package com.foodapp.data.repository

import ApiService
import com.foodapp.data.model.Product
import retrofit2.Call
import retrofit2.http.GET

class ProductRepository {
    private val APIService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)

    fun GetProduct(Id : String) : Call<Product> {
        return APIService.getProductId(Id);
    }
}