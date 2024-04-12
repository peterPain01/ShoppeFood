package com.foodapp.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.view.adapter.GridAdapter
import com.foodapp.view.adapter.HorizontalAdapter
import com.foodapp.view.adapter.VerticalAdapter
import com.foodapp.utils.FakeData

class Search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }

    override fun onStart() {
        super.onStart()

        val recyclerView_horizontal = findViewById<RecyclerView>(R.id.search_recyclerView_horizontal)
        val recyclerView_vertical = findViewById<RecyclerView>(R.id.search_recyclerView_vertical)
        val gridView = findViewById<RecyclerView>(R.id.search_gridView)

        val dummyList = FakeData.createDummyData()
        recyclerView_vertical.layoutManager = LinearLayoutManager(this)
        recyclerView_horizontal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        gridView.layoutManager = GridLayoutManager(this, 2)


        val adapter_horizontal = HorizontalAdapter(listOf("Burger", "Sandwich", "Pizza", "Pasta", "BeefSteak"), R.layout.item_keyword)
        val adapter_vertical = VerticalAdapter(dummyList, R.layout.item_suggest_res)
        val adapte_grid = GridAdapter(dummyList, R.layout.item_grid)

        recyclerView_horizontal.adapter = adapter_horizontal
        recyclerView_vertical.adapter = adapter_vertical
        gridView.adapter = adapte_grid
    }
}