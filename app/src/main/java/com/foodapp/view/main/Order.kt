package com.foodapp.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.DishItems
import com.foodapp.utils.FakeData
import com.foodapp.view.adapter.CartListAdapter
import kotlin.system.exitProcess

class Order : AppCompatActivity() {
    private lateinit var total_tv: TextView
    private lateinit var list: RecyclerView
    private lateinit var dishes: MutableList<DishItems>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        total_tv = findViewById<TextView>(R.id.activity_order_total)
        dishes = FakeData.fakeDishes().map { DishItems(it, 10) }.toMutableList()
        list = findViewById<RecyclerView>(R.id.activity_order_list)
        val updateTotal = {
            val total = dishes.sumOf { it.count * it.dish.price }
            total_tv.text = String.format(null, "$%.2f", total)
        }
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = CartListAdapter(dishes, R.layout.cart_item, updateTotal)
        updateTotal()
    }
}