package com.foodapp.view.main

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.utils.FakeData
import com.foodapp.view.adapter.RestaurantAdapter
import com.foodapp.view.adapter.reviewAdapter

class Admin_Page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin_page)

        val dummyList = FakeData.createDummyData()
        val adapter_vertical = RestaurantAdapter(dummyList, R.layout.item_grid_admin_restaurant)
        val recyclerView_vertical = findViewById<RecyclerView>(R.id.admin_page_recycleview)

        recyclerView_vertical.layoutManager = GridLayoutManager(this, 1)
        recyclerView_vertical.adapter = adapter_vertical
    }
}
