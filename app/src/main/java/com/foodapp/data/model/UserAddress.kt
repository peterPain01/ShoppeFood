package com.foodapp.data.model

import java.io.Serializable

data class UserAddress(
    var name: String = "",
    var street: String = "",
    var type: String = "Home", // Home/Company/Other
    var latlng: MapPosition = MapPosition(0.0, 0.0)
): Serializable

