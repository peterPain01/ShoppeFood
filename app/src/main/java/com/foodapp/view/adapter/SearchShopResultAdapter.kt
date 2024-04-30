package com.foodapp.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.Shop
import com.foodapp.view.main.restaurant_view

class SearchShopResultAdapter(private val context: Context, private val shopList: List<Shop>) : RecyclerView.Adapter<SearchShopResultAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(shopList[position])
        holder.layoutProduct.setOnClickListener{
            onClickGoToShopPage(shopList[position]._id)
        }
    }
    private fun onClickGoToShopPage(shopId : String)
    {
        val intent = Intent(context, restaurant_view::class.java)
        intent.putExtra("shopId", shopId)
        context.startActivity(intent)
    }

    override fun getItemCount(): Int {
        return shopList.count()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shop_img = itemView.findViewById<ImageView>(R.id.item_search_product_image)
        val shop_name = itemView.findViewById<TextView>(R.id.item_search_product_name)
        val shop_open_hour = itemView.findViewById<TextView>(R.id.item_search_product_open_hour)
        val shop_close_hour = itemView.findViewById<TextView>(R.id.item_search_product_close_hour)
        val layoutProduct = itemView.findViewById<LinearLayout>(R.id.item_search_product_layout)
        fun bind(data: Shop) {
            Glide.with(itemView.context)
                .load(data.image)
                .into(shop_img)
            shop_name.text = data.name
            shop_open_hour.text = data.openHour
            shop_close_hour.text = data.closeHour
        }
    }
}

