package com.foodapp.viewmodel

import ApiService
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Product
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.data.repository.UserRepository
import com.foodapp.view.adapter.UserComment.UserCommentAdapter
import retrofit2.Call
import retrofit2.Response

class FoodPaymentViewModel(val id: String,sessionManager: SessionManager, val displayMsg: (String?) -> Unit): ViewModel() {
    private val service = RetrofitClient.retrofit.create(ApiService::class.java)
    private val userService = UserRepository(sessionManager).create(ApiService::class.java)
    var product: MutableLiveData<Product>  = MutableLiveData(Product())
    var count: MutableLiveData<Int> = MutableLiveData(1)
    var totalPrice: MutableLiveData<Double> = MutableLiveData(0.0)
    var totalOriginalPrice: MutableLiveData<String> = MutableLiveData("")
    val commentAdapter: MutableLiveData<UserCommentAdapter> = MutableLiveData()

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
                    commentAdapter.value = UserCommentAdapter(productInfo.product_reviews)
                    updatePrice()
                } else {
                    displayMsg(body.message)
                }
            }

            override fun onFailure(call: Call<ApiResult<Product>>, t: Throwable) {
                displayMsg(t.message)
            }

        })
    }
    fun updatePrice() {
        totalPrice.value = (count.value ?: 1) * (product.value?.product_discounted_price ?: 0.0)
        if (product.value?.product_original_price != product.value?.product_discounted_price) {
            totalOriginalPrice.value = String.format(
                "%.2f",
                (count.value ?: 1) * (product.value?.product_original_price ?: 0.0)
            )
        }
    }
    fun incCount() {
        count.value = count.value!! + 1;
        updatePrice()
    }
    fun decCount() {
        if (count.value!! > 1) {
            count.value = count.value!! - 1
            updatePrice()
        }
    }
    fun addToCart(done: () -> Unit) {
        userService.addToCart(id, count.value!!).enqueue(object: retrofit2.Callback<ApiResult<Nothing>> {
            override fun onResponse(
                call: Call<ApiResult<Nothing>>,
                response: Response<ApiResult<Nothing>>
            ) {
                if (response.code() == 200) {
                    displayMsg("Successfully add product to cart!")
                } else {
                    displayMsg(response.errorBody().toString())
                }
                done()
            }

            override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                displayMsg(t.message)
                done()
            }
        })
    }
}