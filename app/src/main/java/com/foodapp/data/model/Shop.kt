package com.foodapp.data.model

import java.sql.Time

// Danh sach mon an List<mon an>
// Review
// Voucher
class Shop (
    val _id: String = "",
    var name: String = "",
    var image: String = "",
    var description: String = "",
    var phone: String = "",
    var position: Position? = null,
    var category: List<Category> = listOf(),
    var status: String = "",
    var avg_rating: Double = 0.0,
    var openHour: Time? = null,
    var closeHour: Time? = null
)
