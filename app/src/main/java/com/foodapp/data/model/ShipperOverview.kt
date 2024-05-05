package com.foodapp.data.model

import com.foodapp.helper.helper

data class ShipperOverview(
    val revenue: Double = 0.0,
    val balance: Double = 0.0,
) {
    val revenueStr: String
        get() = helper.formatCurrency(revenue)
    val balanceStr: String
        get() = helper.formatCurrency(balance)
}
