package com.foodapp.data.model

import java.sql.Time

data class Restaurant(
    val id: String,
    var name: String,
    var imageUrl: String,
    var rating: Double,
    var location: String,
    var openHour: Time,
    var closeHour: Time
)