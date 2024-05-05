package com.foodapp.data.model

data class ItemMyFood(
    val id: String,
    val imageUrl: String,
    val tag: String,
    val price: Double,
    val star: Double,
    val review: String,
    val name: String,
    val isDraft: Boolean
)
