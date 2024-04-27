package com.foodapp.utils

import com.foodapp.data.model.Dish
import com.foodapp.data.model.DishItems
import com.foodapp.data.model.ItemMyFood
import com.foodapp.data.model.MapPosition
import com.foodapp.data.model.NotificationAdmin
import com.foodapp.data.model.Order
import com.foodapp.data.model.OrderRunning
import com.foodapp.data.model.Review
import com.foodapp.data.model.Shop
import com.foodapp.data.model.UserAddress
import okhttp3.internal.toImmutableList
import java.sql.Time
import java.util.Date

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
                shop = Shop(
                    _id = "ID_$i",
                    name = "Restaurant $i",
                    image = "https://images.foody.vn/res/g119/1181120/prof/s280x175/image-686e0cf3-240130123556.jpeg",
                    avg_rating = 4.0 + (i % 3),
                    openHour = Time((i + 9) * 3600000L),
                    closeHour = Time((i + 18) * 3600000L),
                    description = "OK",
                    phone = "0123456789",
                    category = listOf(),
                    status = "active",
                ),
                address = "",
                date = Date()
            ))
        }
        return orders
    }
    fun createDummyData(): List<Shop> {
        val dummyData = mutableListOf<Shop>()

        for (i in 1..8) {
            val shop = Shop(
                _id = "ID_$i",
                name = "Restaurant $i",
                image = "https://images.foody.vn/res/g119/1181120/prof/s280x175/image-686e0cf3-240130123556.jpeg",
                avg_rating = 4.0 + (i % 3),
                openHour = Time((i + 9) * 3600000L),
                closeHour = Time((i + 18) * 3600000L),
                description = "OK",
                phone = "0123456789",
                category = listOf(),
                status = "active",
            )
            dummyData.add(shop)
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

    fun createReview(): List<Review> {
        val dummyData = mutableListOf<Review>()

        for (i in 1..8) {
            val order =  Review(
                content = "Tanbir Ahmed Placed a new order",
                img = "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg",
                time = "20/12/2020",
                rating = "4"
            )
            dummyData.add(order)
        }
        return dummyData
    }

    fun createAddresses(): List<UserAddress> {
        val result = mutableListOf<UserAddress>()
        for (i in 1..10) {
            result.add(
                UserAddress(
                    name = "Home %d".format(i),
                    street = "123 Dang Vinh Tuong, Phuong 7, Quan Phu Nhuan",
                    latlng = MapPosition(0.0, 0.0),
                    type = if (i%3 == 0) "Home" else if (i%3 == 1) "Company" else "Other"
                )
            )
        }
        return result.toImmutableList()
    }
}
