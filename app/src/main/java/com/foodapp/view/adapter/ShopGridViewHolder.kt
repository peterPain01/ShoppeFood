package com.foodapp.view.adapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.Shop
import com.foodapp.view.main.food_payment

class ShopGridViewHolder(itemView: View) : GridAdapter.ViewHolder<Shop>(itemView)  {
    val imageView: ImageView = itemView.findViewById(R.id.imageUrl)
    val name: TextView = itemView.findViewById(R.id.name)
    val desc: TextView = itemView.findViewById(R.id.desc)

    override fun bind(data: Shop) {
        Glide.with(itemView.context)
            .load(data.image)
            .into(imageView)
        name.text = data.name
        desc.text = data.description
    }
}
