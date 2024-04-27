package com.foodapp.data.model

import java.util.Date

data class CartProduct(
    val productId: String = "",
    val name: String = "",
    val image: String = "",
    val unit_price: Double = 0.0,
    var quantity: Int =  0
)

data class Cart(
    val _id: String = "",
    val cart_state: String = "active",
    val cart_products: MutableList<CartProduct> = mutableListOf(),
    val cart_count_product: Int = 0,
    val cart_userId: String = "",
    val createdOn: Date = Date(),
    val modifiedOn: Date = Date(),
    val cart_note: String = "",
) {
    val totalPrice: Double
        get() = cart_products.sumOf { it.unit_price * it.quantity }
}