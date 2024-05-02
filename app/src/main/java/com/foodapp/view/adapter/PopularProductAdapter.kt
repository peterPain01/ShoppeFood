package com.foodapp.view.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.Product

class PopularProductAdapter (private val dataList: List<Product>) :
    RecyclerView.Adapter<PopularProductAdapter.ViewHolder>() {
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        private val productImage : ImageView = itemView.findViewById(R.id.item_popular_product_image)
        private var productName : TextView= itemView.findViewById(R.id.item_popular_product_name)
        private var productPrice : TextView= itemView.findViewById(R.id.item_popular_product_price)
        private var addToCartButton : ImageView = itemView.findViewById(R.id.item_popular_product_addToCart)
        private var productSold : TextView = itemView.findViewById(R.id.item_popular_product_sold)

        fun bind(data: Product)
        {
            Glide.with(itemView.context)
                .load(data.product_thumb)
                .into(productImage)
            productName.text = data.product_name
            productPrice.text = data.product_discounted_price.toString() + "₫"
            productSold.text = data.product_sold.toString() + " Sold"

            addToCartButton.setOnClickListener{
                // TODO: Add to Cart Logic
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_popular_product, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

}

