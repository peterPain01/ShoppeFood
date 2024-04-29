package com.foodapp.view.main

import ApiService
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.replace
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Shop
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.databinding.ActivitySearchBinding
import com.foodapp.view.adapter.GridAdapter
import com.foodapp.view.adapter.HorizontalAdapter
import com.foodapp.view.adapter.VerticalAdapter
import com.foodapp.utils.FakeData
import com.foodapp.view.Dialog_fragment.Fragment_Init_Search
import com.foodapp.view.Dialog_fragment.Fragment_Search
import com.foodapp.view.adapter.KeyWordRecentAdapter
import retrofit2.Call
import retrofit2.Response
import java.util.ArrayList

class Search : AppCompatActivity() {
    lateinit var cancel_btn : TextView
    lateinit var search_bar : EditText
    val service = RetrofitClient.retrofit.create(ApiService::class.java)
    lateinit var recyclerView_recent_keyw_hz : RecyclerView
    lateinit var binding :ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_search)

    }

    override fun onStart() {
        super.onStart()
        replaceFragment(Fragment_Init_Search())
        cancel_btn= findViewById(R.id.search_cancel)
        search_bar = findViewById(R.id.search_searchBar)
//        recyclerView_recent_keyw_hz = findViewById(R.id.search_recent_keyword_recyclerView_vertical)
//        recyclerView_recent_keyw_hz.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//
//        val keyWordApdapter = KeyWordRecentAdapter(listOf("Cơm tấm", "Chả iu em", "Tà tưa"))
//        recyclerView_recent_keyw_hz.adapter = keyWordApdapter

        initEvent();
    }

    // Flow
    // Bat su kien go nut o search bat
    // Gui thong tin search len API va nhan ve ket qua
    // Hien thi ket qua
    // khi user chon thi luu ket qua vao shared preferences

    private fun initEvent()
    {
        cancel_btn.setOnClickListener{
            this.finish()
        }
        search_bar.addTextChangedListener(searchBarWatcher)

    }

    val searchBarWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            val userInput = s.toString()
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val userInput = s.toString()
            if(userInput.isNullOrEmpty())
            {
                replaceFragment(Fragment_Init_Search())
            }
            else
                replaceFragment(Fragment_Search())
            // call API
//            service.getRelatedSearchString(userInput).enqueue(object : retrofit2.Callback<ApiResult<List<String>>> {
//                override fun onResponse(call: Call<ApiResult<List<String>>>, response: Response<ApiResult<List<String>>>) {
//                    if (response.code() == 200) {
//                        val body = response.body()
//                        if (body != null) {
//                            Log.i("METAAAAAA", body.metadata.toString())
//                            recyclerView_recent_keyw_hz.adapter = KeyWordRecentAdapter(body.metadata)
//                        } else {
//                            Log.d("getRelatedSearchString", "[shop/get related string] Missing body")
//                        }
//                    } else {
////                        Toast.makeText(context, response.body()?.message ?: "", Toast.LENGTH_LONG).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<ApiResult<List<String>>>, t: Throwable) {
//                    TODO("Not yet implemented")
//                }
//            })
        }

        override fun afterTextChanged(s: Editable?) {
            val userInput = s.toString()
        }
    }

    fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.search_fragment_container, fragment)
        fragmentTransaction.commit()
    }

}