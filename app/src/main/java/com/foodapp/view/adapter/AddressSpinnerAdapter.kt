package com.foodapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.foodapp.R
import com.foodapp.data.model.UserAddress

class AddressSpinnerAdapter(context: Context, val items: List<UserAddress>)
    :ArrayAdapter<UserAddress>(context, android.R.layout.simple_spinner_dropdown_item, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }
    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_address_spinner, parent, false)
        val name = view.findViewById<TextView>(R.id.item_address_spinner_name)
        val address = view.findViewById<TextView>(R.id.item_address_spinner_street)
        val image = view.findViewById<ImageView>(R.id.item_address_spinner_image)
        val data = items[position]

        name.text = data.name ?: ""
        address.text = data.street ?: ""
        if (data.type.lowercase() == "home") {
            image.setImageResource(R.drawable.ic_house)
        } else if (data.type?.lowercase() == "company") {
            image.setImageResource(R.drawable.ic_work)
        } else {
            image.setBackgroundColor(0x101010)
        }
        return view
    }
}