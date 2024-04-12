package com.foodapp.utils

import com.foodapp.data.model.Dish
import com.foodapp.data.model.DishItems
import com.foodapp.data.model.Order
import com.foodapp.data.model.Restaurant
import com.foodapp.data.model.ItemMyFood
import com.foodapp.data.model.NotificationAdmin
import com.foodapp.data.model.OrderRunning

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
    
    // fake data
    fun createRunningOrder(): List<OrderRunning> {
        val dummyData = mutableListOf<OrderRunning>()

        for (i in 1..8) {
            val order = OrderRunning(
                tag = "#breakfast",
                name = "Chicken Thai Biriyani",
                price = 60.0,
                imageUrl = "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg"
            )
            dummyData.add(order)
        }
        return dummyData
    }

    fun createItemMyFood(): List<ItemMyFood> {
        val dummyData = mutableListOf<ItemMyFood>()

        for (i in 1..8) {
            val order = ItemMyFood(
                imageUrl = "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg",
                tag = "breakfast",
                price = 60.0,
                star = 4.9,
                review = "(10 reviews)",
                name = "Chicken Thai Biriyani"
            )
            dummyData.add(order)
        }
        return dummyData
    }

    fun createNotification(): List<NotificationAdmin> {
        val dummyData = mutableListOf<NotificationAdmin>()

        for (i in 1..8) {
            val order =  NotificationAdmin(
                id = i.toString(),
                content = "Tanbir Ahmed Placed a new order",
                imageUrl = "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg",
                time = "20 min ago"
            )
            dummyData.add(order)
        }
        return dummyData
    }
}
