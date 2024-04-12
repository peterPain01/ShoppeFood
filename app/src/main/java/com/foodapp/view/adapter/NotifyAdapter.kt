package com.foodapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.NotificationAdmin
import com.foodapp.helper.helper

class NotifyAdapter(private val dataList: List<NotificationAdmin>, private val res : Int) : RecyclerView.Adapter<NotifyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotifyAdapter.ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.item_notifications_img)
        val content = itemView.findViewById<TextView>(R.id.item_notifications_content)
        val time = itemView.findViewById<TextView>(R.id.item_notifications_time)

        fun bind(item: NotificationAdmin) {
            img?.let { imageView ->
                helper.ShowImageUrl(
                    item.imageUrl,
                    imageView
                )
            }
            content.text = item.content
            time.text = item.time
        }
    }
}
