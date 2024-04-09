package com.foodapp.utils

import com.foodapp.data.model.Dish
import com.foodapp.data.model.DishItems
import com.foodapp.data.model.Order
import com.foodapp.data.model.Restaurant
import java.sql.Time

object FakeData {
    fun fakeDishes(): List<Dish> {
        val dishes = mutableListOf<Dish>()
        for (i in 1..10) {
            dishes.add(fakeDish())
        }
        return dishes
    }
    fun fakeDish(): Dish {
        return Dish(
            id = "",
            name = "Spaghetti",
            price = 10.0,
            imageUrl = "https://t3.ftcdn.net/jpg/01/09/75/90/360_F_109759077_SVp62TBuHkSn3UsGW4dBOm9R0ALVetYw.jpg"
        )
    }
    fun createFakeOrder(): List<Order> {
        val orders = mutableListOf<Order>()
        for (i in 1..10) {
            orders.add(Order(
                id = "",
                userId = "",
                dishes = fakeDishes().map { DishItems(it, kotlin.random.Random(10).nextInt(5, 10)) },
                restaurant = Restaurant(
                    id = "ID_$i",
                    name = "Restaurant $i",
                    imageUrl = "https://images.foody.vn/res/g119/1181120/prof/s280x175/image-686e0cf3-240130123556.jpeg",
                    rating = 4.0 + (i % 3),
                    location = "Location $i",
                    openHour = Time((i + 9) * 3600000L),
                    closeHour = Time((i + 18) * 3600000L)
                ),
                address = "",
            ))
        }
        return orders
    }
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
