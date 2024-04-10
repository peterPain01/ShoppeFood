package com.foodapp.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.DishItems
import com.foodapp.utils.FakeData
import com.foodapp.view.adapter.CartListAdapter

class Order : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        val list = findViewById<RecyclerView>(R.id.activity_order_list)
        val dishes = FakeData.fakeDishes().map { DishItems(it, 10) }
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = CartListAdapter(dishes, R.layout.cart_item)
    }
}