package com.foodapp.view.main

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.utils.FakeData
import com.foodapp.view.adapter.OrderHistoryListAdapter
import com.foodapp.view.adapter.OrderOngoingListAdapter

class MyOrder : AppCompatActivity() {
    private val ACTIVE_COLOR = (0xFFFF6622).toInt()
    private val INACTIVE_COLOR = (0xFFA5A7B9).toInt()
    private lateinit var item_list: RecyclerView
    private lateinit var ongoing_btn: Button
    private lateinit var history_btn: Button
    private enum class Tab { Ongoing, History }
    private var current_tab: Tab = Tab.History // placeholder, init with setter later
        set(value) {
            if (field != value) {
                field = value
                if (value == Tab.Ongoing) {
                    ongoing_btn.setTextColor(ACTIVE_COLOR)
                    ongoing_btn.backgroundTintList = ColorStateList.valueOf(ACTIVE_COLOR)
                    history_btn.setTextColor(INACTIVE_COLOR)
                    history_btn.backgroundTintList = ColorStateList.valueOf(INACTIVE_COLOR)
                    item_list.adapter = OrderOngoingListAdapter(FakeData.createFakeOrder(), R.layout.item_ongoing_order)
                } else if (value == Tab.History) {
                    history_btn.setTextColor(ACTIVE_COLOR)
                    history_btn.backgroundTintList = ColorStateList.valueOf(ACTIVE_COLOR)
                    ongoing_btn.setTextColor(INACTIVE_COLOR)
                    ongoing_btn.backgroundTintList = ColorStateList.valueOf(INACTIVE_COLOR)
                    item_list.adapter = OrderHistoryListAdapter(FakeData.createFakeOrder(), R.layout.item_completed_order)
                } else {
                    throw Exception("Unknown type: " + value.name)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order)
    }

    override fun onStart() {
        super.onStart()
        item_list = findViewById<RecyclerView>(R.id.activity_my_order_item_list)
        item_list.layoutManager = LinearLayoutManager(this)
        ongoing_btn = findViewById<Button>(R.id.activity_my_order_ongoing_btn)
        ongoing_btn.setOnClickListener {
            current_tab = Tab.Ongoing
        }
        history_btn = findViewById<Button>(R.id.activity_my_order_history_btn)
        history_btn.setOnClickListener {
            current_tab = Tab.History
        }

        current_tab = Tab.Ongoing
    }
}