package com.foodapp.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.view.adapter.HorizontalAdapter
import com.foodapp.view.adapter.VerticalAdapter
import com.foodapp.utils.FakeData

class Homepage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        val dummyList = FakeData.createDummyData()

        val recyclerView_vertical: RecyclerView = findViewById<RecyclerView>(R.id.recyclerView_vertical)
        val recyclerView_horizontal: RecyclerView = findViewById<RecyclerView>(R.id.recyclerView_horizontal)

        recyclerView_vertical.layoutManager = LinearLayoutManager(this)
        recyclerView_horizontal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val dataList = dummyList
        val adapter_ver = VerticalAdapter(dataList, R.layout.item_vertical)
        val adapter_hori = HorizontalAdapter(listOf("All", "Pizza", "Bread", "Pasta", "BeefSteak"), R.layout.item_horizontal)
        recyclerView_vertical.adapter = adapter_ver
        recyclerView_horizontal.adapter = adapter_hori
    }

}