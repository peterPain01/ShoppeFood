package com.foodapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.Order
import com.foodapp.helper.helper

class OrderOngoingListAdapter(val orders: List<Order>, val res: Int): RecyclerView.Adapter<DataViewHolder<Order>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orders.count()
    }

    override fun onBindViewHolder(holder: DataViewHolder<Order>, position: Int) {
        holder.bind(orders[position])
    }

    inner class ViewHolder(itemView: View): DataViewHolder<Order>(itemView) {
        val title: TextView = itemView.findViewById<TextView>(R.id.item_ongoing_order_title)
        val image: ImageView = itemView.findViewById<ImageView>(R.id.item_ongoing_order_image)
        val price: TextView = itemView.findViewById<TextView>(R.id.item_ongoing_order_price)
        val count: TextView = itemView.findViewById<TextView>(R.id.item_ongoing_order_count)

        override fun bind(data: Order) {
            title.text = data.order_shop.name
            Glide.with(itemView.context)
                .load(data.order_shop.image)
                .into(image)
            price.text = String.format("%s VND", helper.formatter(data.order_totalPrice.toInt()))
            count.text = String.format("%d items", data.order_listProducts.sumOf { it.quantity })
        }
    }
}