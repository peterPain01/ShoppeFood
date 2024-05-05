package com.foodapp.viewmodel

import ApiService
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.ShipperOverview
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Response

class MoneyManageViewModel(sessionManager: SessionManager, displayMsg: (String) -> Unit): ViewModel() {
    private val userService = UserRepository(sessionManager).create(ApiService::class.java)
    val overview: MutableLiveData<ShipperOverview> = MutableLiveData()
    init {
        userService.getShipperRevenueOverview().enqueue(object: retrofit2.Callback<ApiResult<ShipperOverview>> {
            override fun onResponse(
                call: Call<ApiResult<ShipperOverview>>,
                response: Response<ApiResult<ShipperOverview>>
            ) {
                if (response.isSuccessful) {
                    overview.value = response.body()?.metadata!!
                } else {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<ShipperOverview>>, t: Throwable) {
                displayMsg(t.stackTraceToString())
            }
        })
    }
}