package com.foodapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.Restaurant
import com.foodapp.helper.helper

class RestaurantAdapter(private val dataList: List<Restaurant>, private val res : Int) : RecyclerView.Adapter<RestaurantAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantAdapter.ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Restaurant) {
            
        }
        // binding data
    }
}