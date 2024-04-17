package com.foodapp.data.model

data class MapPosition(
    var longitude: Double,
    var latitude: Double
)

data class Address(
    val name: String,
    val address: String,
    val position: MapPosition,
    val type: String
)

