package com.foodapp.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.UserAddress

class AddressAdapter(private val userAddresses: MutableList<UserAddress>,
                     private val res: Int,
                     val onClickItem: (UserAddress?, Int) -> Unit,
                     val onDeleteItem: () -> Unit):
    RecyclerView.Adapter<AddressAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userAddresses.count() ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userAddresses[position], position)
    }

    fun updateAt(index: Int, addr: UserAddress) {
        userAddresses[index] = addr;
        notifyItemChanged(index)
    }
    fun add(addr: UserAddress) {
        Log.d("FOODAPP:AddressAdapter", userAddresses.size.toString())
        userAddresses.add(addr)
        notifyItemInserted(userAddresses.size)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.item_address_image)
        private val name = itemView.findViewById<TextView>(R.id.item_address_name)
        private val address = itemView.findViewById<TextView>(R.id.item_address_address)
        private val editBtn = itemView.findViewById<ImageButton>(R.id.item_address_edit_btn)
        private val deleteBtn = itemView.findViewById<ImageButton>(R.id.item_address_delete_btn)
        fun bind(data: UserAddress?, index: Int) {
            name.text = data?.name ?: ""
            address.text = data?.street ?: ""
            if (data?.type?.lowercase() == "home") {
                image.setImageResource(R.drawable.ic_house)
            } else if (data?.type?.lowercase() == "company") {
                image.setImageResource(R.drawable.ic_work)
            } else {
                image.setImageResource(R.drawable.ic_address)
            }
            itemView.setOnClickListener {
                onClickItem(data, index)
            }
            editBtn.setOnClickListener {
                onClickItem(data, index)
            }
            deleteBtn.setOnClickListener {
                notifyItemRemoved(index)
                userAddresses.removeAt(index)
                onDeleteItem()
            }
        }
    }
}