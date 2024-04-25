package com.foodapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class GridAdapter<T>(private val items: List<T>,
                     private val item_res : Int,
                     val factory: (View) -> ViewHolder<T>) : RecyclerView.Adapter<GridAdapter.ViewHolder<T>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(item_res, parent, false)
        return this.factory(view)
    }
    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        val data = items[position]
       holder.bind(data)
    }
    override fun getItemCount(): Int {
        return items.size
    }
    open class ViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView) {
        open fun bind(data: T) {
        }
    }
}
