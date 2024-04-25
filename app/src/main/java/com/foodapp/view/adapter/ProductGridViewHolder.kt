package com.foodapp.view.adapter

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.Product
import com.foodapp.view.main.food_payment

class ProductGridViewHolder(itemView: View) : GridAdapter.ViewHolder<Product>(itemView)  {
    val imageView: ImageView = itemView.findViewById(R.id.imageUrl)
    val name: TextView = itemView.findViewById(R.id.name)
    val desc: TextView = itemView.findViewById(R.id.desc)
    override fun bind(data: Product) {
        Glide.with(itemView.context)
            .load(data.product_thumb)
            .into(imageView)
        name.text = data.product_name
        desc.text = data.product_description
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, food_payment::class.java)
            intent.putExtra("productId", data._id)
            itemView.context.startActivity(intent);
        }
    }
}