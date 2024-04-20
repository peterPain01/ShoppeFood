package com.foodapp.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id") val id: String,
    var email: String,
    var password: String
)