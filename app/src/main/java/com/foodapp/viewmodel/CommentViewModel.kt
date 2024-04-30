package com.foodapp.viewmodel

import ApiService
import android.content.Context
import android.util.Log
import android.widget.Adapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Category
import com.foodapp.data.model.Review
import com.foodapp.data.model.Shop
import com.foodapp.data.model.auth.AuthResponse
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.data.repository.UserRepository
import com.foodapp.view.adapter.MultipleChoiceSpinnerAdapter
import com.foodapp.view.adapter.reviewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommentViewModel  (private val context: Context, private val adapter: RecyclerView){
    private val service = UserRepository(SessionManager(context)).create(ApiService::class.java)
    init {
        service.getComment().enqueue(object :
            Callback<ApiResult<List<Review>>> {
            override fun onResponse(call: Call<ApiResult<List<Review>>>, response: Response<ApiResult<List<Review>>>) {
                if (response.isSuccessful) {
                    Log.i("hello", response.body().toString());
                    val adapter_vertical = reviewAdapter(response.body()!!.metadata, R.layout.item_review)

                    adapter.layoutManager = GridLayoutManager(context, 1)
                    adapter.adapter = adapter_vertical
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Toast.makeText(context, "Failed to get statistic: $errorMessage", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ApiResult<List<Review>>>, t: Throwable) {
                Toast.makeText(context, t.message ?: "Unknown error", Toast.LENGTH_LONG).show()
            }
        })
    }
}