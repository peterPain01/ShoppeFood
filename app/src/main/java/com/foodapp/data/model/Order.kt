package com.foodapp.data.model

import java.util.Date

data class DishItems(val dish: Dish, var count: Int);

data class Order(
    val id: String,
    var userId: String,
    var dishes: List<DishItems>,
    var shop: Shop,
    var address: String,
    var date: Date
) {
    val totalPrice: Double
        get() = this.dishes.sumOf { it.count * it.dish.price }
    val totalDishes: Int
        get() = this.dishes.sumOf { it.count }
}
