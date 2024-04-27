package com.foodapp.data.model

import java.util.Date

data class OrderUser(
    val _id: String = "",
    val fullname: String = "",
    val phone: String = ""
)

data class OrderShop(
    val _id: String = "",
    val name: String = "",
    val image: String = ""
)

data class Order(
    val _id: String = "",
    val order_state: String = "pending",
    val order_user: OrderUser = OrderUser(),
    val order_shop: OrderShop = OrderShop(),
    val order_totalPrice: Double = 0.0,
    val order_subPrice: Double = 0.0,
    val order_listProducts: List<CartProduct> = listOf(),
    val order_paymentMethod: String = "cash",
    val createdAt: Date = Date()
)
