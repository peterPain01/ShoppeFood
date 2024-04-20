package com.foodapp.data.model

data class MapPosition(
    var lng: Double,
    var lat: Double
)

data class Position(
    var address: String,
    var latLng: MapPosition
)
