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
        val dummyList = FakeData.createDummyData()

        val recyclerView_vertical: RecyclerView = findViewById<RecyclerView>(R.id.homepage_recyclerView_vertical)
        val recyclerView_horizontal: RecyclerView = findViewById<RecyclerView>(R.id.homepage_recyclerView_horizontal)

        recyclerView_vertical.layoutManager = LinearLayoutManager(this)
        recyclerView_horizontal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val dataList = dummyList
        service.getAllCategories().enqueue(object: retrofit2.Callback<List<Category>> {
            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {
                if (response.code() == 200) {
                    val body = response.body()
                    Log.d("Homepage:Category", body.toString())
                    if (body != null) {
                        recyclerView_horizontal.adapter = HorizontalAdapter(body, R.layout.item_horizontal)
                    } else {
                        Log.e("API", "[categories] Missing body")
                        Toast.makeText(null, "Server not working", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(null, response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                Toast.makeText(null, t.message, Toast.LENGTH_LONG).show()
            }
        })

        service.getTopRated(10).enqueue(object: retrofit2.Callback<List<Shop>> {
            override fun onResponse(call: Call<List<Shop>>, response: Response<List<Shop>>) {
                if (response.code() == 200) {
                    val body = response.body()
                    Log.d("Homepage:Shop", body.toString())
                    if (body != null) {
                        recyclerView_vertical.adapter = VerticalAdapter(body, R.layout.item_vertical)
                    } else {
                        Log.e("API", "[shop/top-rated] Missing body")
                        Toast.makeText(null, "Server not working", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(null, response.message(), Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Shop>>, t: Throwable) {
                Toast.makeText(null, t.message, Toast.LENGTH_LONG).show()
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