package com.foodapp.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.view.adapter.GridAdapter
import com.foodapp.view.adapter.VerticalAdapter
import com.foodapp.utils.FakeData
import com.google.android.material.button.MaterialButton

class CategoryDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_detail)
    }

    override fun onStart() {
        super.onStart()
        val dummyList = FakeData.createDummyData()

        // GRID VIEW
        val gridView = findViewById<RecyclerView>(R.id.category_detail_gridView)
        gridView.layoutManager = GridLayoutManager(this, 2)
        // val adapte_grid = GridAdapter(dummyList, R.layout.item_grid_checkout)
        // gridView.adapter = adapte_grid

        // RECYCLER VERTICAL VIEW
        val recyclerView_vertical: RecyclerView = findViewById<RecyclerView>(R.id.category_detail_recyclerView_vertical)
        val adapter_vertical = VerticalAdapter(dummyList, R.layout.item_vertical)
        recyclerView_vertical.layoutManager = LinearLayoutManager(this)
        recyclerView_vertical.adapter = adapter_vertical

        // SPINNER CATETGORY
        val spinnerButton : AppCompatButton = findViewById<AppCompatButton>(R.id.category_detail_btnAsSpinner)
        val items : List<String> =  listOf("Burgers", "Pizza", "BeefSteak", "Bread")

        spinnerButton.setOnClickListener {
            val popupMenu = PopupMenu(this, spinnerButton)
            for (item in items) {
                popupMenu.menu.add(item)
            }
            popupMenu.setOnMenuItemClickListener { menuItem ->
                spinnerButton.text = menuItem.title
                true
            }
            popupMenu.show()
        }
    }
}