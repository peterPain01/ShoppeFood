package com.foodapp.view.adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R

class KeyWordRecentAdapter(private val keywordList: List<String>) : RecyclerView.Adapter<KeyWordRecentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_keyword, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(keywordList[position])
    }

    override fun getItemCount(): Int {
        return keywordList.count()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemRecentSearchString: TextView = itemView.findViewById(R.id.tv_search_keyword)

        fun bind(keyword: String) {
            itemRecentSearchString.text = keyword
        }
    }
}
