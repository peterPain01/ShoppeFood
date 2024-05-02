package com.foodapp.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class DataViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(data: T)
}