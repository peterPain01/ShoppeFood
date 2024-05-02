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
import java.text.SimpleDateFormat

class OrderHistoryListAdapter(val orders: List<Order>, val res: Int): RecyclerView.Adapter<DataViewHolder<Order>>() {
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
        val title: TextView = itemView.findViewById<TextView>(R.id.item_completed_order_title)
        val image: ImageView = itemView.findViewById<ImageView>(R.id.item_completed_order_image)
        val price: TextView = itemView.findViewById<TextView>(R.id.item_completed_order_price)
        val count: TextView = itemView.findViewById<TextView>(R.id.item_completed_order_count)
        val date: TextView = itemView.findViewById<TextView>(R.id.item_completed_order_date)

        override fun bind(data: Order) {
            title.text = data.order_shop.name
            Glide.with(itemView.context)
                .load(data.order_shop.image)
                .into(image)
            price.text = String.format("$%.2f", data.order_totalPrice)
            count.text = String.format("%d items", data.order_listProducts.sumOf { it.quantity })
            val formatter = SimpleDateFormat("dd MMM, HH:mm")
            date.text = formatter.format(data.updatedAt)
        }
    }
}