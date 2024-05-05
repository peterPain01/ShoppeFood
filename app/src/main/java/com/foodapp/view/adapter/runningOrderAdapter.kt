package com.foodapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.OrderRunning
import com.foodapp.data.model.Running
import com.foodapp.helper.helper

class runningOrderAdapter (private val dataList: List<Running>, private val res : Int) : RecyclerView.Adapter<runningOrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): runningOrderAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: runningOrderAdapter.ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val priceTextView: TextView = itemView.findViewById(R.id.item_grid_running_price)
        private val nameTextView: TextView = itemView.findViewById(R.id.item_grid_running_name)

        fun bind(order: Running) {
            priceTextView.text = "${helper.formatCurrency(order.order_totalPrice.toDouble())}"
            nameTextView.text = order.order_user.phone
        }
    }
}
