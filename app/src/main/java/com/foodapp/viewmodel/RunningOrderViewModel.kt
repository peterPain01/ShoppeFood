package com.foodapp.viewmodel

import ApiService
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.ListProductRunning
import com.foodapp.data.model.Product
import com.foodapp.data.model.Running
import com.foodapp.data.model.User
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import com.foodapp.utils.FakeData
import com.foodapp.view.adapter.GridAdapter
import com.foodapp.view.adapter.PopularProductAdapter
import com.foodapp.view.adapter.runningOrderAdapter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class RunningOrderViewModel(context: Context, recyclerView_vertical: RecyclerView, sessionManager: SessionManager): ViewModel()  {
    var service = UserRepository(sessionManager).create(ApiService::class.java)
    var context = context
    init {
        service.getAllPending().enqueue(object: retrofit2.Callback<ApiResult<List<Running>>> {
            override fun onResponse(
                call: Call<ApiResult<List<Running>>>,
                response: Response<ApiResult<List<Running>>>
            ) {
                if (response.code() == 200) {
                    val running = response.body()!!.metadata
                    val adapter_vertical = runningOrderAdapter(running, R.layout.item_grid_running)
                    recyclerView_vertical.layoutManager = GridLayoutManager(context, 1)
                    recyclerView_vertical.adapter = adapter_vertical
                } else {

                }
            }

            override fun onFailure(call: Call<ApiResult<List<Running>>>, t: Throwable) {

            }
        })


    }
}