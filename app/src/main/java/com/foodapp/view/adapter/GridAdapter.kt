package com.foodapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.Restaurant

class GridAdapter(private val items: List<Restaurant>, private val item_res : Int) : RecyclerView.Adapter<GridAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(item_res, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position]
       holder.bind(data)
    }
    override fun getItemCount(): Int {
        return items.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageUrl)
        val name: TextView = itemView.findViewById(R.id.name)
        val desc: TextView = itemView.findViewById(R.id.desc)
        fun bind(data: Restaurant) {
            Glide.with(itemView.context)
                .load(data.imageUrl)
                .into(imageView)
            name.text = data.name
        }
    }
}
