package com.foodapp.data.model.auth

import com.foodapp.data.model.User
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("tokens")
    val tokens: Tokens,

    @SerializedName("user")
    val user: User
)