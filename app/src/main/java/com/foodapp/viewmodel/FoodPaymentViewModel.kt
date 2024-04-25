package com.foodapp.viewmodel

import ApiService
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Product
import com.foodapp.data.repository.RetrofitClient
import retrofit2.Call
import retrofit2.Response

class FoodPaymentViewModel(id: String, displayError: (String?) -> Unit, showImage: (String?) -> Unit) {
    private val service = RetrofitClient.retrofit.create(ApiService::class.java)
    var product: MutableLiveData<Product>  = MutableLiveData(Product())
    var count: Int = 0

    init {
        service.getProductId(id).enqueue(object: retrofit2.Callback<ApiResult<Product>> {
            override fun onResponse(
                call: Call<ApiResult<Product>>,
                response: Response<ApiResult<Product>>
            ) {
                val body = response.body()
                val productInfo = body!!.metadata
                if (response.code() == 200) {
                    product.value = productInfo
                    showImage(product.value?.product_thumb)
                } else {
                    displayError(body.message)
                }
            }

            override fun onFailure(call: Call<ApiResult<Product>>, t: Throwable) {
                displayError(t.message)
            }

        })
    }
}