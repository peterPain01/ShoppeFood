package com.foodapp.data.model

import com.foodapp.data.NameDescImage

data class Product(
    val _id: String,
    val product_sold: Int,
    val product_like: Int,
    val category: String,
    val product_shop: Shop ?= null,
    val product_name: String,
    val product_price: Int,
    val product_thumb: String,
    val product_description: String,
): NameDescImage {
    override val name: String
        get() = product_name
    override val desc: String
        get() = product_description
    override val image: String
        get() = product_thumb
}
