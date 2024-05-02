package com.foodapp.data.repository

import ApiService
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.auth.SessionManager
import retrofit2.Call
import retrofit2.Response

class LikeAPI(private val context: Context) {
    val userService = UserRepository(SessionManager(context)).create(ApiService::class.java)
    fun postLikeToServer(context: Context, shopId: String) {
        userService.userShopLike(shopId).enqueue(object : retrofit2.Callback<ApiResult<Nothing>> {
            override fun onResponse(
                call: Call<ApiResult<Nothing>>,
                response: Response<ApiResult<Nothing>>
            ) {
            }

            override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                Log.e("Homepage:Category", t.message!!)
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun postUnLikeToServer(context: Context, shopId: String) {
        userService.userShopUnlike(shopId).enqueue(object : retrofit2.Callback<ApiResult<Nothing>> {
            override fun onResponse(
                call: Call<ApiResult<Nothing>>,
                response: Response<ApiResult<Nothing>>
            ) {
            }

            override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                Log.e("Homepage:Category", t.message!!)
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

}