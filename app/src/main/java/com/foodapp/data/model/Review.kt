package com.foodapp.data.model

import java.io.Serializable

data class Review(
    var _id: String = "",
    var comment_userId: String = "",
    var comment_userAvatar: String = "",
    var comment_shopId: String = "",
    var productId: String = "",
    var comment_content_text: String = "",
    var comment_content_image: String = "",
    var comment_star: Int = 0,
    var comment_title: String = "",
    var comment_date: String = ""
    // reply of shop
): Serializable
