package com.foodapp.view.Dialog_fragment

import ApiService
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.view.adapter.KeyWordRecentAdapter
import retrofit2.Call
import retrofit2.Response

class Fragment_Search(val relatedString: String) :Fragment(R.layout.fragment_search) {
    lateinit var relatedString_recycler : RecyclerView
    val service = RetrofitClient.retrofit.create(ApiService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        init(view)
        return view;
    }
    fun init(view : View)
    {
        relatedString_recycler = view.findViewById(R.id.F_Search_related_keywords)
        relatedString_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val itemDecoration : RecyclerView.ItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        relatedString_recycler.addItemDecoration(itemDecoration)

        getRelatedStringFromServer(relatedString)
    }

    fun getRelatedStringFromServer(relatedString: String)
    {
        service.getRelatedSearchString(relatedString).enqueue(object : retrofit2.Callback<ApiResult<List<String>>> {
                override fun onResponse(call: Call<ApiResult<List<String>>>, response: Response<ApiResult<List<String>>>) {
                    if (response.code() == 200) {
                        val body = response.body()
                        if (body != null) {
                            Log.i("METAAAAAA", body.metadata.toString())
                            relatedString_recycler.adapter = KeyWordRecentAdapter(requireContext(), body.metadata)
                        } else {
                            Log.d("getRelatedSearchString", "[shop/get related string] Missing body")
                        }
                    } else {
                        Toast.makeText(context, response.body()?.message ?: "", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ApiResult<List<String>>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }
}