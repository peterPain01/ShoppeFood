package com.foodapp.data.model

data class Product(
    var _id: String = "",
    var product_sold: Int = 0,
    var product_like: Int = 0,
    var category: String = "",
    var product_shop: Shop ?= null,
    var product_name: String = "",
    var product_discounted_price: Double = 0.0,
    var product_original_price: Double = 0.0,
    var product_thumb: String = "",
    var product_description: String = "",
    var product_reviews : List<Review> = emptyList()
)
