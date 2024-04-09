package com.foodapp.data.model

data class DishItems(val dish: Dish, val count: Int);

data class Order(
    val id: String,
    var userId: String,
    var dishes: List<DishItems>,
    var restaurant: Restaurant,
    var address: String,
) {
    val totalPrice =  {
        this.dishes.sumOf { it.count * it.dish.price }
    }
}
