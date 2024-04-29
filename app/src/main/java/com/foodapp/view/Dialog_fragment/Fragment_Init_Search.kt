package com.foodapp.view.Dialog_fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.foodapp.R
import com.foodapp.view.adapter.KeyWordRecentAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Fragment_Init_Search :Fragment(R.layout.fragment_init_search) {
    private lateinit var recyclerView_recent_keywords: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_init_search, container, false)
        init(view)
        return view;
    }
    private fun init(view: View) {
        recyclerView_recent_keywords = view.findViewById(R.id.F_initSearch_recent_keyword_recyclerView_vertical)
    }

    override fun onStart() {
        super.onStart()

        recyclerView_recent_keywords.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        recyclerView_recent_keywords.adapter = KeyWordRecentAdapter(listOf("Com tam", "Tra sua", "Banh mi"))

        val itemDecoration : RecyclerView.ItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerView_recent_keywords.addItemDecoration(itemDecoration)

    }
}

// nhan vao item => get item value va truyen sang fragment 2 de tim kiem, ghi vao shared preference
fun getStringList(context: Context?, key: String): List<String> {
    val sharedPreferences = context?.getSharedPreferences("recentSearch", Context.MODE_PRIVATE)
    val gson = Gson()
    val json = sharedPreferences?.getString(key, null)
    val type = object : TypeToken<List<String>>() {}.type
    return gson.fromJson(json, type) ?: listOf()
}

fun saveStringList(context: Context?, key: String, stringList: List<String>) {
    val sharedPreferences = context?.getSharedPreferences("recentSearch", Context.MODE_PRIVATE)
    val editor = sharedPreferences?.edit()
    val gson = Gson()
    val json = gson.toJson(stringList)
    editor?.putString(key, json)
    editor?.apply()
}
