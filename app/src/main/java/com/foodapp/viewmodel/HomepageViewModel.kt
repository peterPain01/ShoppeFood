package com.foodapp.viewmodel

import ApiService
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Category
import com.foodapp.data.model.Shop
import com.foodapp.data.model.UserOverview
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.data.repository.UserRepository
import com.foodapp.view.adapter.HorizontalAdapter
import com.foodapp.view.adapter.VerticalAdapter
import com.foodapp.view.adapter.VerticalShopViewHolder
import retrofit2.Call
import retrofit2.Response

class HomepageViewModel(val sessionManager: SessionManager, val displayMsg: (String) -> Unit): ViewModel() {
    private val userService = UserRepository(sessionManager).create(ApiService::class.java)
    val overview: MutableLiveData<UserOverview> = MutableLiveData(UserOverview())
    val service = RetrofitClient.retrofit.create(ApiService::class.java)
    val categoryAdapter: MutableLiveData<HorizontalAdapter> = MutableLiveData()
    val topRatedShopAdapter: MutableLiveData<VerticalAdapter<Shop, VerticalShopViewHolder>> = MutableLiveData()
    init {
        loadOverview()
        (if (sessionManager.isLogin()) userService else service).getTopRated(10).enqueue(object : retrofit2.Callback<ApiResult<List<Shop>>> {
            override fun onResponse(
                call: Call<ApiResult<List<Shop>>>,
                response: Response<ApiResult<List<Shop>>>
            ) {
                if (response.code() == 200) {
                    val body = response.body()
                    if (body != null) {
                        topRatedShopAdapter.value = VerticalAdapter(body.metadata, R.layout.item_vertical, VerticalShopViewHolder::class.java)
                    } else {
                        displayMsg("Server not working")
                    }
                } else {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<List<Shop>>>, t: Throwable) {
                displayMsg(t.message ?: "")
            }
        })
        service.getAllCategories().enqueue(object : retrofit2.Callback<ApiResult<List<Category>>> {
            override fun onResponse(
                call: Call<ApiResult<List<Category>>>,
                response: Response<ApiResult<List<Category>>>
            ) {
                if (response.code() == 200) {
                    val body = response.body()
                    Log.d("Homepage:Category", body.toString())
                    if (body != null) {
                        categoryAdapter.value = HorizontalAdapter(body.metadata, R.layout.item_horizontal)
                    } else {
                        displayMsg("Server not working")
                    }
                } else {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<List<Category>>>, t: Throwable) {
                displayMsg(t.message ?: "")
            }
        })
    }
    fun loadOverview() {
        if (sessionManager.isLogin()) userService.getUserOverview().enqueue(object: retrofit2.Callback<ApiResult<UserOverview>> {
            override fun onResponse(
                call: Call<ApiResult<UserOverview>>,
                response: Response<ApiResult<UserOverview>>
            ) {
                if (response.code() == 200) {
                    overview.value = response.body()?.metadata!!
                } else {
                    displayMsg(response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<ApiResult<UserOverview>>, t: Throwable) {
                displayMsg(t.message ?: "")
            }
        })
    }
}