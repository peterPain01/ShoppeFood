package com.foodapp.view.adapter

import com.foodapp.R
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.foodapp.data.model.Shop
import com.foodapp.view.main.restaurant_view

class VerticalShopViewHolder(itemView: View) : DataViewHolder<Shop>(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.item_vertical_image)
    private val titleTextView: TextView = itemView.findViewById(R.id.item_vertical_name)
    private val ratingTextView = itemView.findViewById<TextView>(R.id.item_vertical_rating)
    private val category: TextView = itemView.findViewById(R.id.item_vertical_category)
    private val distance : TextView = itemView.findViewById(R.id.item_vertical_distance)

    override fun bind(data: Shop) {
        Glide.with(itemView.context)
            .load(data.image)
            .into(imageView)
        titleTextView.text = data.name
        category.text = data.category.joinToString(separator = " - ") { it.name }
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, restaurant_view::class.java)
            intent.putExtra("shopId", data._id)
            itemView.context.startActivity(intent)
        }
        ratingTextView.text = data.avg_rating.toString()
        distance.text = data.distance.toString()
    }
}
