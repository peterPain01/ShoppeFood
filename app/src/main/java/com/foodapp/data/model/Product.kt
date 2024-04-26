package com.foodapp.data.model

data class Product(
    val _id: String = "",
    val product_sold: Int = 0,
    val product_like: Int = 0,
    val category: String = "",
    val product_shop: Shop ?= null,
    val product_name: String = "",
    val product_discounted_price: Double = 0.0,
    val product_original_price: Double = 0.0,
    val product_thumb: String = "",
    val product_description: String = "",
    val product_comments: Array<String>? = null
)
