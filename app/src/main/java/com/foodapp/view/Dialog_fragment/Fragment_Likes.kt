package com.foodapp.view.Dialog_fragment

import ApiService
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Shop
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.data.repository.UserRepository
import com.foodapp.view.adapter.like.LikeSortApdater
import com.foodapp.view.adapter.SearchShopResultAdapter
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Response


class Fragment_Likes :Fragment(R.layout.fragment_likes) {
    lateinit var tabLayout : TabLayout
    lateinit var recyclerView : RecyclerView
    lateinit var spinnerSort : Spinner
    private lateinit var userService : ApiService

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
        spinnerSort = view.findViewById(R.id.f_likes_spinner_sort)
        userService = UserRepository(SessionManager(requireContext())).create(ApiService::class.java)


        val listSortItems = listOf<String>("All Services", "Food", "Vegan", "Drink", "Flowers", "Fast Food")
        val adapter = LikeSortApdater(requireContext(), listSortItems)
        spinnerSort.adapter = adapter

        recyclerView.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        val itemDecoration : RecyclerView.ItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)

        getListOfShopFromServer(requireContext(), "latest");
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    if(tab.position == 0) {
                        getListOfShopFromServer(requireContext(), "latest");
                    }
                    else if(tab.position == 1)
                    {
                        getListOfShopFromServer(requireContext(), "nearby");
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val temp : String = "Do Some thing"
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val temp : String = "Do some thing"
            }
        })

    }

    override fun onStart() {
        super.onStart()
    }

    private fun getListOfShopFromServer(context : Context ,  sortBy : String)
    {
        userService.getShopUserLiked(sortBy).enqueue(object : retrofit2.Callback<ApiResult<List<Shop>>> {
            override fun onResponse(
                call: Call<ApiResult<List<Shop>>>,
                response: Response<ApiResult<List<Shop>>>
            ) {
                if (response.code() == 200) {
                    val body = response.body()
                    Log.d("SearchDetail Activity", body.toString())
                    if (body != null) {
                        recyclerView.adapter =
                            SearchShopResultAdapter(context, body.metadata, 1)
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




