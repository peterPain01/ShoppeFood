package com.foodapp.data.model

import com.google.gson.annotations.SerializedName
import java.sql.Time

// Danh sach mon an List<mon an>
// Review
// Voucher
class Shop (
    val _id: String = "",
    var name: String = "",
    var image: String = "",
    var avatar: String = "",
    var description: String = "",
    var phone: String = "",
    var position: UserAddress? = null,
    var category: List<Category> = listOf(),
    var status: String = "",
    var avg_rating: Double = 0.0,
    @SerializedName("open_hour")
    var openHour: String? = null,
    @SerializedName("close_hour")
    var closeHour: String? = null,
    var isUserLiked: Boolean = false,

//  @param:distance: count from shop to homes user
    var distance : Double = 0.0
)
