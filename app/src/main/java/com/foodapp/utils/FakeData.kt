package com.foodapp.utils

import com.foodapp.data.model.Restaurant
import java.sql.Time

object FakeData {
    fun createDummyData(): List<Restaurant> {
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