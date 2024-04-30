package com.foodapp

import ApiService
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Category
import com.foodapp.data.model.Shop
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.view.adapter.HorizontalAdapter
import com.foodapp.view.adapter.SearchShopResultAdapter
import retrofit2.Call
import retrofit2.Response

class SearchDetailActivity : AppCompatActivity() {
    lateinit var searchBar : EditText
    lateinit var finishBtn : ImageButton
    lateinit var recyclerView_shop: RecyclerView

    val service = RetrofitClient.retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_detail)
    }

    override fun onStart() {
        super.onStart()
        val bundle = intent.extras
        val keySearch = bundle?.getString("keySearch")

        finishBtn = findViewById(R.id.search_detail_finish)
        searchBar = findViewById(R.id.search_detail_searchBar)
        recyclerView_shop = findViewById(R.id.search_detail_recyclerView)

        searchBar.setText(keySearch)
        recyclerView_shop.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val itemDecoration : RecyclerView.ItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        recyclerView_shop.addItemDecoration(itemDecoration)
        getListOfShopFromServer(this, keySearch!!)

        initEvent()

    }

    private fun initEvent()
    {
        finishBtn.setOnClickListener {
            this.finish()
        }
    }

    private fun getListOfShopFromServer(context : Context , keySearch : String)
    {
        service.getShopBySearchString(keySearch).enqueue(object : retrofit2.Callback<ApiResult<List<Shop>>> {
            override fun onResponse(
                call: Call<ApiResult<List<Shop>>>,
                response: Response<ApiResult<List<Shop>>>
            ) {
                if (response.code() == 200) {
                    val body = response.body()
                    Log.d("SearchDetail Activity", body.toString())
                    if (body != null) {
                        recyclerView_shop.adapter =
                            SearchShopResultAdapter(context, body.metadata)
                    } else {
                        Log.e("API", "[categories] Missing body")
                        Toast.makeText(context, "Server not working", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(context, response.body()?.message ?: "", Toast.LENGTH_LONG)
                        .show()
                }
            }
            override fun onFailure(call: Call<ApiResult<List<Shop>>>, t: Throwable) {
                Log.e("Homepage:Category", t.message!!)
                Toast.makeText(context, t.message, Toast.LENGTH_LONG).show()
            }
        })

    }

}