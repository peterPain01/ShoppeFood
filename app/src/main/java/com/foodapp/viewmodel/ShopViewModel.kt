package com.foodapp.viewmodel

import ApiService
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.DashBoard
import com.foodapp.data.model.ItemMyFood
import com.foodapp.data.model.OrderRunning
import com.foodapp.data.model.Product
import com.foodapp.data.model.Shop
import com.foodapp.data.model.Statistic
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import com.foodapp.helper.helper
import com.foodapp.view.adapter.GridAdapter
import com.foodapp.view.adapter.PopularProductAdapter
import com.foodapp.view.adapter.runningOrderAdapter
import com.foodapp.view.adapter.ProductGridViewHolder
import com.foodapp.view.adapter.ProductShopGridViewHolder
import com.foodapp.view.adapter.myFoodAdapter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class ShopViewModel  (private val context: Context){
    private val service = UserRepository(SessionManager(context)).create(ApiService::class.java)
    fun getStatistic(view: View, gridView: RecyclerView, lineChart: LineChart) {
        service.getStatistic().enqueue(object : Callback<ApiResult<DashBoard>> {
            override fun onResponse(call: Call<ApiResult<DashBoard>>, response: Response<ApiResult<DashBoard>>) {
                if (response.isSuccessful) {
                    val request = response.body()
                    val data= request!!.metadata;
                    view.findViewById<TextView>(R.id.dash_board_location5).text = data.numPendingOrder.toString();
                    view.findViewById<TextView>(R.id.dash_board_location6).text = data.numShippingOrder.toString();
                    view.findViewById<TextView>(R.id.dash_board_revenue).text = data.totalRevenueToday.toString();
                    val adapte_grid = GridAdapter<Product>(data.trendingProducts, R.layout.item_grid_checkout, ::ProductShopGridViewHolder)
                    gridView.layoutManager = GridLayoutManager(context, 2)
                    gridView.adapter = adapte_grid
                    drawLineChart(lineChart, data.reportRevenue)
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

    fun getProduct(view: View, recyclerView_vertical: RecyclerView, type: String) {
        var serviceProduct: Call<ApiResult<List<Product>>> ?= null;
        if (type == "all"){
            serviceProduct = service.getAllProduct();
        }
        else if(type == "publish")
        {
            serviceProduct = service.getPublish();
        }
        else if(type == "unPublish") {
            serviceProduct = service.getUnPublish();
        }

        serviceProduct?.enqueue(object : Callback<ApiResult<List<Product>>> {
                override fun onResponse(call: Call<ApiResult<List<Product>>>, response: Response<ApiResult<List<Product>>>) {
                    if (response.isSuccessful) {
                        val request = response.body()
                        val datas= request!!.metadata;

                        Log.i("aaa", datas.toString())
                        val dummyData = mutableListOf<ItemMyFood>()

                        datas.forEach {
                            val order = ItemMyFood(
                                id = it._id,
                                imageUrl = it.product_thumb,
                                tag = "breakfast",
                                price = it.product_discounted_price,
                                star = Random.nextInt(30, 40) / 10.0,
                                review = "(0 reviews)",
                                name = it.product_name,
                                isDraft = it.isDraft
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
    public var shop: MutableLiveData<Shop> = MutableLiveData(Shop(""))
    fun getInfo(imageView: ImageView)
    {
        service.getShopInfo(SessionManager(context).fetchAuthToken().userId!!).enqueue(object: retrofit2.Callback<ApiResult<Shop>> {
            override fun onResponse(
                call: Call<ApiResult<Shop>>,
                response: Response<ApiResult<Shop>>
            ) {
                if (response.code() == 200) {
                    val body = response.body()
                    val shopInfo = body!!.metadata
                    shop.value = shopInfo
                    helper.ShowImageUrl(shopInfo.avatar, imageView)
                    }
                else {
                    //displayError(response.body()?.message)
                }
            }

            override fun onFailure(call: Call<ApiResult<Shop>>, t: Throwable) {
               // displayError(t.message)
            }
        })
    }

    fun drawLineChart(lineChart: LineChart, lists: List<Statistic>) {
        val entries = arrayListOf<Entry>();
        lists.forEach {
            entries.add(Entry(it.time.toFloat(), it.revenue.toFloat()));
        }

        val dataSet = LineDataSet(entries, "Total Revenue this month")

        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.RED

        val lineData = LineData(dataSet)

        lineChart.data = lineData

        lineChart.description.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(true)
        lineChart.setDrawGridBackground(false)
        lineChart.setBackgroundColor(Color.WHITE)

        val xAxis: XAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val leftAxis: YAxis = lineChart.axisLeft
        leftAxis.setDrawGridLines(true)

        val rightAxis: YAxis = lineChart.axisRight
        rightAxis.isEnabled = false

        val legend: Legend = lineChart.legend
        legend.form = Legend.LegendForm.LINE
    }
}