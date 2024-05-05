package com.foodapp.data.model

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date

data class Shipper(
    val _id: String = "",
    var avatar: String = "",
    var vehicle_image: String = "",
    var license_plate_number: String = "",
    var fullname: String = "Pham Hoang Gia Huy",
    var isActive: Boolean = false   ,
    var balance: Double = 0.0,
    // only have lat lng
//    var currentPosition: UserAddress? = null,



    )