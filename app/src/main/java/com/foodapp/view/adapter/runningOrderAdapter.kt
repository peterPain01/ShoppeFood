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
import com.foodapp.helper.helper

class runningOrderAdapter (private val dataList: List<OrderRunning>, private val res : Int) : RecyclerView.Adapter<runningOrderAdapter.ViewHolder>() {

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
        private val tagTextView: TextView = itemView.findViewById(R.id.item_grid_running_tag)
//        private val imageView: ImageView = itemView.findViewById(R.id.item_grid_running_img)
//        private val priceTextView: TextView = itemView.findViewById(R.id.item_grid_running_price)
//        private val nameTextView: TextView = itemView.findViewById(R.id.item_grid_running_name)

        fun bind(order: OrderRunning) {
//            tagTextView.text = order.tag
//            imageView?.let { imageView ->
//                helper.ShowImageUrl(
//                    order.imageUrl,
//                    imageView
//                )
//            }
//            priceTextView.text = "$${order.price}"
  //          nameTextView.text = "1"
        }
    }
}