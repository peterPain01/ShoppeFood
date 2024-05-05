package com.foodapp.view.adapter

import ApiService
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.DashBoard
import com.foodapp.data.model.ItemMyFood
import com.foodapp.data.model.Product
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import com.foodapp.helper.helper
import com.foodapp.view.main.seller_review
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random


class myFoodAdapter(private val dataList: List<ItemMyFood>, private val res : Int) : RecyclerView.Adapter<myFoodAdapter.ViewHolder>() {
    private var service : ApiService ?= null;
    private var context : Context ?= null;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myFoodAdapter.ViewHolder {
        context = parent.context;
        val view = LayoutInflater.from(context).inflate(res, parent, false)
        service = UserRepository(SessionManager(context!!)).create(ApiService::class.java)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: myFoodAdapter.ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.item_my_food_total_BackGroundFood)
        val nameFood = itemView.findViewById<TextView>(R.id.item_my_food_total_running_name)
        val tag = itemView.findViewById<TextView>(R.id.item_my_food_total_tag)
        val rating = itemView.findViewById<TextView>(R.id.item_my_food_total_rating)
        val review = itemView.findViewById<TextView>(R.id.item_my_food_total_review)
        val price = itemView.findViewById<TextView>(R.id.item_my_food_total_running_price)
        val status = itemView.findViewById<com.google.android.material.switchmaterial.SwitchMaterial>(R.id.item_my_food_switch)

        fun bind(itemfood: ItemMyFood) {
            nameFood.text = itemfood.name;
            if(!itemfood.isDraft){
                tag.text = "Publish";
            }else{
                tag.text = "UnPublish";
            }
            rating.text = itemfood.star.toString();
            review.text = itemfood.review;
            price.text = helper.formatter(itemfood.price.toInt()) + " VND";
            img?.let { imageView ->
                helper.ShowImageUrl(
                    itemfood.imageUrl,
                    imageView
                )
            }
            status.isChecked = !itemfood.isDraft
            status.setOnCheckedChangeListener { buttonView, isChecked ->
                if (!isChecked){
                    tag.text = "UnPublish";
                    service?.unPublish(itemfood.id)?.enqueue(object : Callback<ApiResult<Nothing>> {
                        override fun onResponse(
                            call: Call<ApiResult<Nothing>>,
                            response: Response<ApiResult<Nothing>>
                        ) {
                            if (response.isSuccessful) {

                            } else {
                                Log.i("message",  "Failed to get statistic:")
                            }
                        }

                        override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                            Log.i("message",  t.message ?: "Unknown error")
                        }
                    })
                }
                else {
                    tag.text = "Publish";
                    service?.publish(itemfood.id)?.enqueue(object : Callback<ApiResult<Nothing>> {
                        override fun onResponse(
                            call: Call<ApiResult<Nothing>>,
                            response: Response<ApiResult<Nothing>>
                        ) {
                            if (response.isSuccessful) {

                            } else {
                                Log.i("message",  "Failed to get statistic:")
                            }
                        }

                        override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                            Log.i("message",  t.message ?: "Unknown error")
                        }
                    })
                }
            }
        }
    }
}