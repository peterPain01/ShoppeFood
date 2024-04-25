package com.foodapp.view.main

import ApiService
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Category
import com.foodapp.data.model.Shop
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.data.repository.UserRepository
import com.foodapp.view.adapter.HorizontalAdapter
import com.foodapp.view.adapter.VerticalAdapter
import com.foodapp.utils.FakeData
import retrofit2.Call
import retrofit2.Response

class Homepage : AppCompatActivity() {
    val service = RetrofitClient.retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)

        val recyclerView_vertical: RecyclerView = findViewById<RecyclerView>(R.id.homepage_recyclerView_vertical)
        val recyclerView_horizontal: RecyclerView = findViewById<RecyclerView>(R.id.homepage_recyclerView_horizontal)

        recyclerView_vertical.layoutManager = LinearLayoutManager(this)
        recyclerView_horizontal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val context = this

        service.getAllCategories().enqueue(object: retrofit2.Callback<ApiResult<List<Category>>> {
            override fun onResponse(
                call: Call<ApiResult<List<Category>>>,
                response: Response<ApiResult<List<Category>>>
            ) {
                if (response.code() == 200) {
                    val body = response.body()
                    Log.d("Homepage:Category", body.toString())
                    if (body != null) {
                        recyclerView_horizontal.adapter = HorizontalAdapter(body.metadata, R.layout.item_horizontal)
                    } else {
                        Log.e("API", "[categories] Missing body")
                        Toast.makeText(context, "Server not working", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(context, response.body()!!.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ApiResult<List<Category>>>, t: Throwable) {
                Log.e("Homepage:Category", t.message!!)
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })

        service.getTopRated(10).enqueue(object: retrofit2.Callback<ApiResult<List<Shop>>> {
            override fun onResponse(call: Call<ApiResult<List<Shop>>>, response: Response<ApiResult<List<Shop>>>) {
                if (response.code() == 200) {
                    val body = response.body()
                    if (body != null) {
                        recyclerView_vertical.adapter = VerticalAdapter(body.metadata, R.layout.item_vertical)
                    } else {
                        Log.d("FOODAPP:Homepage:TopRated", "[shop/top-rated] Missing body")
                        Toast.makeText(context, "Server not working", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(context, response.body()!!.message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ApiResult<List<Shop>>>, t: Throwable) {
                Log.e("Homepage:TopRated", t.message!!)
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        val btn = findViewById<AppCompatImageButton>(R.id.homepage_notify)
        val setting = findViewById<AppCompatImageButton>(R.id.homepage_setting)

        setting.setOnClickListener {
            val intent = Intent(this, UserInfo::class.java)
            startActivity(intent);
        }

        btn.setOnClickListener {
            val intent = Intent(this, Order::class.java)
            startActivity(intent);
        }

    }

}