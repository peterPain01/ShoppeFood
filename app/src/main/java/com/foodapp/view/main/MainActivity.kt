package com.foodapp.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.Restaurant
import com.foodapp.adapter.VerticalAdapter
import com.foodapp.data.repository.UserRepository
import java.sql.Time

class MainActivity : AppCompatActivity() {
    // binding
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)
        val dummyList = createDummyData()

        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recyclerView_vertical)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dataList = dummyList
        val adapter = VerticalAdapter(dataList)
        recyclerView.adapter = adapter

        userRepository = UserRepository()

    }

    fun  createDummyData(): List<Restaurant> {
        val dummyData = mutableListOf<Restaurant>()

        for (i in 1..8) {
            val restaurant = Restaurant(
                id = "ID_$i",
                name = "Restaurant $i",
                imageUrl = "https://images.foody.vn/res/g119/1181120/prof/s280x175/image-686e0cf3-240130123556.jpeg",
                rating = 4.0 + (i % 3),
                location = "Location $i",
                openHour = Time((i + 9) * 3600000L),
                closeHour = Time((i + 18) * 3600000L)
            )
            dummyData.add(restaurant)
        }
        return dummyData
    }
}