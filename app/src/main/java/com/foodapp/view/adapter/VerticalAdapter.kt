package com.foodapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.Shop
import com.foodapp.view.main.restaurant_view

class VerticalAdapter<T, K : DataViewHolder<T>>(private val dataList: List<T>, private val res: Int, private val klass: Class<K>) : RecyclerView.Adapter<DataViewHolder<T>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return klass.getDeclaredConstructor(View::class.java).newInstance(view) as DataViewHolder<T>
    }

    override fun onBindViewHolder(holder: DataViewHolder<T>, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
