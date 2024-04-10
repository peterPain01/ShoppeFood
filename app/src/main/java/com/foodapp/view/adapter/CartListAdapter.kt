package com.foodapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.DishItems

class CartListAdapter(private val dataList: List<DishItems>, private val res : Int) : RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position]);
    }

    override fun getItemCount(): Int {
        return dataList.size;
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.cart_item_imageView3)
        private val name = itemView.findViewById<TextView>(R.id.cart_item_textView5)
        private val price = itemView.findViewById<TextView>(R.id.cart_item_textView6)
        private val count = itemView.findViewById<TextView>(R.id.cart_item_textView9)
        fun bind(data: DishItems) {
            Glide.with(itemView.context)
                .load(data.dish.imageUrl)
                .into(image)
            name.text = data.dish.name
            price.text = data.dish.price.toString()
            count.text = data.count.toString()
        }
    }

}