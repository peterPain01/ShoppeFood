package com.foodapp.data.model

data class UserOverview(
    val address: UserAddress = UserAddress(),
    val totalItem: Int = 0
)
