package com.foodapp.data.model

import com.foodapp.helper.helper
import java.util.Date

data class OrderUser(
    val _id: String = "",
    val fullname: String = "",
    val phone: String = "",
    val address: UserAddress = UserAddress()
)

data class OrderShop(
    val _id: String = "",
    val name: String = "",
    val image: String = "",
    val address: UserAddress = UserAddress()
) {
}

data class Order(
    val _id: String = "",
    val order_state: String = "pending",
    val order_user: OrderUser = OrderUser(),
    val order_shop: OrderShop = OrderShop(),
    val order_totalPrice: Double = 0.0,
    val order_subPrice: Double = 0.0,
    val order_listProducts: List<CartProduct> = listOf(),
    val order_paymentMethod: String = "cash",
    val order_shippingFee: Double = 0.0,
    val order_shipperReceive: Double = 0.0,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) {
    val totalPriceString: String
        get() = helper.formatCurrency(order_totalPrice)
    val subPriceString: String
        get() = helper.formatCurrency(order_subPrice)
    val shippingFeeString: String
        get() = helper.formatCurrency(order_shippingFee)
    val shipperReceiveString: String
        get() = helper.formatCurrency(order_shipperReceive)
}

data class Running(
    val _id: String = "",
    val order_user: OrderUser = OrderUser(),
    val order_totalPrice: String = ""
)
