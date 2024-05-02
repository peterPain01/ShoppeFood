package com.foodapp.view.adapter

import ApiService
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.Shop
import com.foodapp.data.repository.LikeAPI
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.view.main.restaurant_view
import retrofit2.Call
import retrofit2.Response

class SearchShopResultAdapter(private val context: Context, private val shopList: List<Shop>, private val likeMode : Int = 0) : RecyclerView.Adapter<SearchShopResultAdapter.ViewHolder>() {
    val service = RetrofitClient.retrofit.create(ApiService::class.java)
    val likeAPI = LikeAPI(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search_shop, parent, false)
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
        private val shop_avatar: ImageView = itemView.findViewById(R.id.item_search_shop_avatar)
        private val shop_name = itemView.findViewById<TextView>(R.id.item_search_shop_name)
        private val shop_open_hour = itemView.findViewById<TextView>(R.id.item_search_shop_open_hour)
        private val shop_close_hour = itemView.findViewById<TextView>(R.id.item_search_shop_close_hour)
        val layoutProduct = itemView.findViewById<LinearLayout>(R.id.item_search_shop_layout)
        private val prefer_tag = itemView.findViewById<TextView>(R.id.item_search_shop_prefer)
        private val like_btn = itemView.findViewById<ImageButton>(R.id.item_search_shop_likeBtn)

        @SuppressLint("ResourceAsColor")
        fun bind(data: Shop) {
            Glide.with(itemView.context)
                .load(data.avatar)
                .into(shop_avatar)
            shop_name.text = data.name
            shop_open_hour.text = data.openHour.toString()
            shop_close_hour.text = data.closeHour.toString()

            if(likeMode == 1)
            {
                like_btn.visibility = View.VISIBLE
                var count : Int = 1
                // default orange color
                val iconDrawable = ContextCompat.getDrawable(context, R.drawable.ic_heart_solid)
                iconDrawable?.setColorFilter(ContextCompat.getColor(context, R.color.orange), PorterDuff.Mode.SRC_IN)
                like_btn.setImageDrawable(iconDrawable)

                like_btn.setOnClickListener {
                    Log.i("COUNT", count.toString())
                    if(count % 2 != 0)
                    {
                        iconDrawable?.setColorFilter(ContextCompat.getColor(context, R.color.grey_500), PorterDuff.Mode.SRC_IN)
                        likeAPI.postUnLikeToServer(context, data._id)
                    }
                    else
                    {
                        iconDrawable?.setColorFilter(ContextCompat.getColor(context, R.color.orange), PorterDuff.Mode.SRC_IN)
                        likeAPI.postLikeToServer(context, data._id)
                    }
                    like_btn.setImageDrawable(iconDrawable)
                    count += 1
                }
            }

            if(data.avg_rating > 4.7)
                prefer_tag.visibility = View.VISIBLE

        }
    }

}

