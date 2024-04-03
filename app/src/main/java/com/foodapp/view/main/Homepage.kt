package com.foodapp.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.adapter.HorizontalAdapter
import com.foodapp.data.model.Restaurant
import com.foodapp.adapter.VerticalAdapter
import com.foodapp.data.repository.UserRepository
import com.foodapp.utils.FakeData
import java.sql.Time

class HomePage : AppCompatActivity() {
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