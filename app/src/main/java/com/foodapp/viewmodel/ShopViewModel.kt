package com.foodapp.viewmodel

import ApiService
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.DashBoard
import com.foodapp.data.model.ItemMyFood
import com.foodapp.data.model.OrderRunning
import com.foodapp.data.model.Product
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import com.foodapp.view.adapter.GridAdapter
import com.foodapp.view.adapter.runningOrderAdapter
import com.foodapp.view.adapter.ProductGridViewHolder
import com.foodapp.view.adapter.myFoodAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class ShopViewModel  (private val context: Context){
    private val service = UserRepository(SessionManager(context)).create(ApiService::class.java)
    fun getStatistic(view: View, gridView: RecyclerView) {
        service.getStatistic().enqueue(object : Callback<ApiResult<DashBoard>> {
            override fun onResponse(call: Call<ApiResult<DashBoard>>, response: Response<ApiResult<DashBoard>>) {
                if (response.isSuccessful) {
                    val request = response.body()
                    val data= request!!.metadata;
                    Log.i("daaa", data.toString());
                    view.findViewById<TextView>(R.id.dash_board_location5).text = data.numPendingOrder.toString();
                    view.findViewById<TextView>(R.id.dash_board_location6).text = data.numShippingOrder.toString();
                    view.findViewById<TextView>(R.id.dash_board_revenue).text = data.totalRevenueToday.toString();
                    val adapte_grid = GridAdapter<Product>(data.trendingProducts, R.layout.item_grid_checkout, ::ProductGridViewHolder)
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

    fun getProduct(view: View, recyclerView_vertical: RecyclerView) {
        service.getProduct().enqueue(object : Callback<ApiResult<List<Product>>> {
            override fun onResponse(call: Call<ApiResult<List<Product>>>, response: Response<ApiResult<List<Product>>>) {
                if (response.isSuccessful) {
                    val request = response.body()
                    val datas= request!!.metadata;

                    Log.i("aaa", datas.toString())
                    val dummyData = mutableListOf<ItemMyFood>()

                    datas.forEach {
                        val order = ItemMyFood(
                            imageUrl = it.product_thumb,
                            tag = "breakfast",
                            price = it.product_discounted_price,
                            star = Random.nextInt(30, 40) / 10.0,
                            review = "(0 reviews)",
                            name = it.product_name
                        )
                        dummyData.add(order);
                    }

                    val adapter_vertical = myFoodAdapter(dummyData, R.layout.item_my_food_total)
                    recyclerView_vertical.layoutManager = GridLayoutManager(context, 1)
                    recyclerView_vertical.adapter = adapter_vertical
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                    Toast.makeText(context, "Failed to get statistic: $errorMessage", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ApiResult<List<Product>>>, t: Throwable) {
                Toast.makeText(context, t.message ?: "Unknown error", Toast.LENGTH_LONG).show()
            }
        })
    }
}