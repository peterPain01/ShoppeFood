package com.foodapp.viewmodel

import androidx.lifecycle.ViewModel
import com.foodapp.data.model.Product
import com.foodapp.data.model.User
import com.foodapp.data.repository.ProductRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel(private val restaurantRepository: ProductRepository): ViewModel() {
    fun getProduct(Id: String) {
        val call: Call<Product> = restaurantRepository.GetProduct(Id);
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {

            }

            override fun onFailure(call: Call<Product>, t: Throwable) {

            }
        })
    }
}