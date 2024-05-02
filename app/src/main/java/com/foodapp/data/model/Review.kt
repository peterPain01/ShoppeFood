package com.foodapp.data.model

data class Review(
    var _id: String = "",
    var comment_userId: String = "",
    var comment_userAvatar: String = "",
    var comment_shopId: String = "",
    var comment_content_text: String = "",
    var comment_content_image: String = "",
    var comment_star: String = "",
    var comment_title: String = "",
    var comment_date: String = ""
    // reply of shop
)
