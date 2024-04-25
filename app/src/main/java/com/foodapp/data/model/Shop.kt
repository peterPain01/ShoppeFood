package com.foodapp.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Time

// Danh sach mon an List<mon an>
// Review
// Voucher
data class Shop (
    @SerializedName("_id") val id: String,
    var name: String = "",
    var image: String = "",
    var description: String = "",
    var phone: String = "",
    var position: Position? = null,
    var category: List<Category> = listOf(),
    var status: String = "",
    var avg_rating: Double = 0.0,
    var openHour: Time? = null,
    var closeHour: Time? = null
) {
}
