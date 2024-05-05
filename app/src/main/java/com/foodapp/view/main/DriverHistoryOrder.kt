package com.foodapp.view.main

import ApiService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Order
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import com.foodapp.databinding.ActivityDriverHistoryOrderBinding
import com.foodapp.view.adapter.OrderHistoryListAdapter
import retrofit2.Call
import retrofit2.Response

class DriverHistoryOrder : AppCompatActivity() {
    private lateinit var binding: ActivityDriverHistoryOrderBinding
    private lateinit var userService: ApiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_history_order)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_driver_history_order)
        binding.lifecycleOwner = this
        userService = UserRepository(SessionManager(this)).create(ApiService::class.java)
    }

    override fun onStart() {
        super.onStart()
        binding.activityDriverHistoryOrderBackBtn.setOnClickListener { this.finish() }
        userService.getShipperHistoryOrders().enqueue(object: retrofit2.Callback<ApiResult<List<Order>>> {
            override fun onResponse(
                call: Call<ApiResult<List<Order>>>,
                response: Response<ApiResult<List<Order>>>
            ) {
                if (response.isSuccessful) {
                    val orders = response.body()?.metadata ?: listOf()
                    binding.activityDriverHistoryOrderList.adapter = OrderHistoryListAdapter(orders, R.layout.item_completed_order)
                } else {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<List<Order>>>, t: Throwable) {
                displayMsg(t.stackTraceToString())
            }
        })
    }

    fun displayMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        Log.d("FOODAPP:driver_info", msg)
    }
}