package com.foodapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.UserAddress

class AddressAdapter(private val userAddresses: List<UserAddress>, private val res: Int): RecyclerView.Adapter<AddressAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userAddresses.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userAddresses[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.item_address_image)
        private val name = itemView.findViewById<TextView>(R.id.item_address_name)
        private val address = itemView.findViewById<TextView>(R.id.item_address_address)
        private val editBtn = itemView.findViewById<ImageButton>(R.id.item_address_edit_btn)
        private val deleteBtn = itemView.findViewById<ImageButton>(R.id.item_address_delete_btn)
        fun bind(data: UserAddress) {
            name.text = data.name
            address.text = data.position.address
            if (data.type == "home") {
                image.setImageResource(R.drawable.ic_house)
            } else if (data.type == "work") {
                image.setImageResource(R.drawable.ic_work)
            } else {
                image.setBackgroundColor(0x101010)
            }
        }
    }
}