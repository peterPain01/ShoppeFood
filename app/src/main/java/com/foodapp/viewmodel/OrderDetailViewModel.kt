package com.foodapp.viewmodel

import ApiService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.CartProduct
import com.foodapp.data.model.Order
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import com.foodapp.view.adapter.VerticalAdapter
import com.foodapp.view.adapter.VerticalProductViewHolder
import retrofit2.Call
import retrofit2.Response

class OrderDetailViewModel(sessionManager: SessionManager, orderId: String, displayMsg: (String) -> Unit): ViewModel() {
    val order: MutableLiveData<Order> = MutableLiveData()
    val userService = UserRepository(sessionManager).create(ApiService::class.java)
    val adapter: MutableLiveData<VerticalAdapter<CartProduct, VerticalProductViewHolder>> = MutableLiveData()

    init {
        userService.getOrderById(orderId).enqueue(object: retrofit2.Callback<ApiResult<Order>> {
            override fun onResponse(
                call: Call<ApiResult<Order>>,
                response: Response<ApiResult<Order>>
            ) {
                if (response.code() == 200) {
                    val data = response.body()?.metadata!!
                    order.value = data
                    adapter.value = VerticalAdapter(data.order_listProducts, R.layout.item_order_detail_product, VerticalProductViewHolder::class.java)
                } else {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<Order>>, t: Throwable) {
                displayMsg(t.message ?: "")
            }
        })
    }
}