package com.foodapp.data.model

data class DashBoard(
    val numPendingOrder: String = "",
    val numShippingOrder: String = "",
    val totalRevenueToday: String = "",
    val trendingProducts: List<Product> = listOf(),
    val reportRevenue: List<Statistic> = listOf(),
    val totalComments : String = "",
    val address: UserAddress = UserAddress()
)
