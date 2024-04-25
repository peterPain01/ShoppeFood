package com.foodapp.data.model

import android.os.Parcelable
import com.foodapp.data.NameDescImage
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.sql.Time

// Danh sach mon an List<mon an>
// Review
// Voucher
class Shop (
    @SerializedName("_id") val id: String,
    override var name: String = "",
    override var image: String = "",
    var description: String = "",
    var phone: String = "",
    var position: Position? = null,
    var category: List<Category> = listOf(),
    var status: String = "",
    var avg_rating: Double = 0.0,
    var openHour: Time? = null,
    var closeHour: Time? = null
): NameDescImage {
    override val desc: String
        get() = description
}
