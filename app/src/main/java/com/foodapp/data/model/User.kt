package com.foodapp.data.model

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date

data class User(
    @SerializedName("_id") val id: String,
    var phone: String,
    var password: String,
): Serializable  {
    var fullname: String? = null
    var avatar: String?   = null
    var email: String?    = null
    var addresses: List<UserAddress> = listOf()
    var birth_date: Date? = null
    var gender: String?   = null
    var roles: Int?       = null
    var created_at: Date? = null
    var bio: String?      = null
    val birthDateString: String?
        @SuppressLint("SimpleDateFormat")
        get() {
            return birth_date?.let { SimpleDateFormat("dd/MM/yyyy").format(it) }
        }
}