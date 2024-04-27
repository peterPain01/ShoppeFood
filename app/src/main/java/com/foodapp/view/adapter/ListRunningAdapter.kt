package com.foodapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.ListProductRunning
import com.foodapp.data.model.NotificationAdmin
import com.foodapp.helper.helper

class ListRunningAdapter (private val dataList: List<ListProductRunning>, private val res : Int) : RecyclerView.Adapter<ListRunningAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListRunningAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListRunningAdapter.ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val content = itemView.findViewById<TextView>(R.id.item_list_product_cart_name)
        val quantity = itemView.findViewById<TextView>(R.id.item_list_product_cart_textView2)

        fun bind(item: ListProductRunning) {
            content.text = item.name
            quantity.text = item.quantity
        }
    }
}