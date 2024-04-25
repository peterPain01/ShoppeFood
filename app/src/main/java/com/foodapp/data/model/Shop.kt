package com.foodapp.data.model

import android.text.Editable
import com.google.gson.annotations.SerializedName
import java.sql.Time

// Danh sach mon an List<mon an>
// Review
// Voucher
data class Shop(
    @SerializedName("_id") val id: String,
    var name: String?,
    var image: String?,
    var description: String?,
    var phone: String?,
    var position: Position?,
    var category: List<String>?,
    var status: String?,
    var avg_rating: Double?,
    var openHour: Time?,
    var closeHour: Time?
)