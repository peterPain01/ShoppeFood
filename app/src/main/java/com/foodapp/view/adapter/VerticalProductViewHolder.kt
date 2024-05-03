package com.foodapp.view.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.CartProduct


class VerticalProductViewHolder(itemView: View): DataViewHolder<CartProduct>(itemView) {
    val name = itemView.findViewById<TextView>(R.id.item_order_detail_product_name)
    val image = itemView.findViewById<ImageView>(R.id.item_order_detail_product_image)
    val price = itemView.findViewById<TextView>(R.id.item_order_detail_product_price)
    val quantity = itemView.findViewById<TextView>(R.id.item_order_detail_product_quantity)

    override fun bind(data: CartProduct) {
        name.text = data.name
        Glide.with(itemView.context)
            .load(data.image)
            .into(image)
        price.text = String.format("%.2f VND", data.unit_price)
        quantity.text = String.format("X %d", data.quantity)
    }
}