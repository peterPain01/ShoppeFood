package com.foodapp.view.Dialog_fragment

import ApiService
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Shop
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.view.adapter.KeyWordRecentAdapter
import com.foodapp.view.adapter.SearchShopResultAdapter
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Response

class Fragment_Likes :Fragment(R.layout.fragment_likes) {
    lateinit var tabLayout : TabLayout
    lateinit var recyclerView : RecyclerView
    val service = RetrofitClient.retrofit.create(ApiService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_likes, container, false)
        init(view)
        return view;
    }
    private fun init(view: View) {
        tabLayout = view.findViewById(R.id.f_likes_tab_layout)
        recyclerView = view.findViewById(R.id.f_likes_recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val itemDecoration : RecyclerView.ItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    if(tab.position == 0) {
                        getListOfShopFromServer(context!!, "latest");
                    }
                    else if(tab.position == 1)
                    {
                        getListOfShopFromServer(context!!, "nearby");
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val temp : String = "do Some thing"
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val temp : String = "do some thing"
            }

        })

    }

    override fun onStart() {
        super.onStart()

    }


    private fun getListOfShopFromServer(context : Context ,  sortBy : String)
    {
        service.getShopUserLiked(sortBy).enqueue(object : retrofit2.Callback<ApiResult<List<Shop>>> {
            override fun onResponse(
                call: Call<ApiResult<List<Shop>>>,
                response: Response<ApiResult<List<Shop>>>
            ) {
                if (response.code() == 200) {
                    val body = response.body()
                    Log.d("SearchDetail Activity", body.toString())
                    if (body != null) {
                        recyclerView.adapter =
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




