package com.foodapp.data.model

data class Product(
    val _id: String,
    val product_sold: Int,
    val product_like: Int,
    val quantity: Int,
    val category: String,
    val sold: Int,
    val like: Int,
    val comments: Array<String>,
    val product_shop: String,
    val product_name: String,
    val product_price: Int,
    val product_thumb: Int,
    val product_description: String,
    val product_comments: Array<String>
)
