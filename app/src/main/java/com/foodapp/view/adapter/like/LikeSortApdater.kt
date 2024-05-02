package com.foodapp.view.adapter.like

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.foodapp.R

class LikeSortApdater(context: Context, items :List<String>) : ArrayAdapter<String>(context ,R.layout.item_likes_sort_selected,  items){

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(R.layout.item_likes_sort_selected, position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(R.layout.item_likes_sort, position, convertView, parent)
    }

    private fun createView(layoutResId: Int, position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(layoutResId, parent, false)

        val textViewId = if (layoutResId == R.layout.item_likes_sort_selected) R.id.item_likes_sort_selected_text else R.id.item_likes_sort_text
        val textView = view.findViewById<TextView>(textViewId)
        textView.text = this?.getItem(position)

        return view
    }
}