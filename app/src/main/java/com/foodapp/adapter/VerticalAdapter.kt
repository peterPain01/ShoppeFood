package com.foodapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.Restaurant

class VerticalAdapter(private val dataList: List<Restaurant>) : RecyclerView.Adapter<VerticalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_vertical, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageUrl)
        private val titleTextView: TextView = itemView.findViewById(R.id.name)
        private val subInfoTextView: TextView = itemView.findViewById(R.id.rating)

        fun bind(data: Restaurant) {
            Glide.with(itemView.context)
                .load(data.imageUrl)
                .into(imageView)
            titleTextView.text = data.name
        }
    }
}
