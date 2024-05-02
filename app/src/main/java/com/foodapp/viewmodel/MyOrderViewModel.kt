package com.foodapp.viewmodel

import ApiService
import com.foodapp.R
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Order
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import com.foodapp.view.adapter.DataViewHolder
import com.foodapp.view.adapter.OrderHistoryListAdapter
import com.foodapp.view.adapter.OrderOngoingListAdapter
import retrofit2.Call
import retrofit2.Response

class MyOrderViewModel(sessionManager: SessionManager, val displayMsg: (String) -> Unit) {
    val adapter: MutableLiveData<RecyclerView.Adapter<DataViewHolder<Order>>> = MutableLiveData()
    val userService = UserRepository(sessionManager).create(ApiService::class.java)

    fun loadHistory() {
        userService.getSuccessOrder().enqueue(object: retrofit2.Callback<ApiResult<List<Order>>> {
            override fun onResponse(
                call: Call<ApiResult<List<Order>>>,
                response: Response<ApiResult<List<Order>>>
            ) {
                if (response.code() == 200) {
                    val completedOrders = response.body()?.metadata!!
                    adapter.value = OrderHistoryListAdapter(completedOrders, R.layout.item_completed_order)
                } else {
                    displayMsg(response.errorBody().toString())
                }
            }
            override fun onFailure(call: Call<ApiResult<List<Order>>>, t: Throwable) {
            }
        })
    }
    fun loadOngoing() {
        userService.getOnGoingOrder().enqueue(object: retrofit2.Callback<ApiResult<List<Order>>> {
            override fun onResponse(
                call: Call<ApiResult<List<Order>>>,
                response: Response<ApiResult<List<Order>>>
            ) {
                if (response.code() == 200) {
                    val ongoingOrders = response.body()?.metadata!!
                    adapter.value = OrderOngoingListAdapter(ongoingOrders, R.layout.item_ongoing_order)
                } else {
                    displayMsg(response.errorBody().toString())
                }
            }
            override fun onFailure(call: Call<ApiResult<List<Order>>>, t: Throwable) {
            }
        })
    }
}