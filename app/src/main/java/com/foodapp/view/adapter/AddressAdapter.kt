package com.foodapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.Address

class AddressAdapter(private val addresses: List<Address>, private val res: Int): RecyclerView.Adapter<AddressAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return addresses.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(addresses[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.item_address_image)
        private val name = itemView.findViewById<TextView>(R.id.item_address_name)
        private val address = itemView.findViewById<TextView>(R.id.item_address_address)
        private val editBtn = itemView.findViewById<ImageButton>(R.id.item_address_edit_btn)
        private val deleteBtn = itemView.findViewById<ImageButton>(R.id.item_address_delete_btn)
        fun bind(data: Address) {
            name.text = data.name
            address.text = data.address
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