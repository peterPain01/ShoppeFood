package com.foodapp.viewmodel

import ApiService
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.DashBoard
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import com.foodapp.view.adapter.GridAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopViewModel  (private val context: Context){
    private val service = UserRepository(SessionManager(context)).create(ApiService::class.java)
    fun getStatistic(view: View, gridView: RecyclerView) {
        service.getStatistic().enqueue(object : Callback<ApiResult<DashBoard>> {
            override fun onResponse(call: Call<ApiResult<DashBoard>>, response: Response<ApiResult<DashBoard>>) {
                if (response.isSuccessful) {
                    val request = response.body()
                    val data= request!!.metadata;
                    view.findViewById<TextView>(R.id.dash_board_location5).text = data.numPendingOrder.toString();
                    view.findViewById<TextView>(R.id.dash_board_location6).text = data.numShippingOrder.toString();
                    view.findViewById<TextView>(R.id.dash_board_revenue).text = data.totalRevenueToday.toString();
                    val adapte_grid = GridAdapter(data.trendingProducts, R.layout.item_grid_checkout)
                    gridView.layoutManager = GridLayoutManager(context, 2)
                    gridView.adapter = adapte_grid
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Toast.makeText(context, "Failed to get statistic: $errorMessage", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ApiResult<DashBoard>>, t: Throwable) {
                Toast.makeText(context, t.message ?: "Unknown error", Toast.LENGTH_LONG).show()
            }
        })
    }
}