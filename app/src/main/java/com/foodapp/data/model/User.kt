package com.foodapp.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("userId") val id: String,
    var phone: String,
    var password: String
)