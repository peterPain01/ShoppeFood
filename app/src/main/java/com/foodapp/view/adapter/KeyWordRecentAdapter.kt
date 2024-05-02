package com.foodapp.view.adapter

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.SearchDetailActivity
import kotlin.coroutines.coroutineContext

class KeyWordRecentAdapter(private val context: Context,private val keywordList: List<String>) : RecyclerView.Adapter<KeyWordRecentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_keyword, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(keywordList[position])
        holder.layoutItem.setOnClickListener{
            onClickGoToSearchDetail(keywordList[position])

        }
    }
    private fun onClickGoToSearchDetail(keySearch :String)
    {
        val intent = Intent(context, SearchDetailActivity::class.java)
        intent.putExtra("keySearch", keySearch)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return keywordList.count()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val itemRecentSearchString: TextView = itemView.findViewById(R.id.tv_search_keyword)
         var layoutItem : ConstraintLayout = itemView.findViewById(R.id.ic_search_keyword_layout)

        fun bind(keyword: String) {
            itemRecentSearchString.text = keyword
        }
    }
}
