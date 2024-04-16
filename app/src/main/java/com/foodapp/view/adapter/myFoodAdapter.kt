package com.foodapp.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.ItemMyFood
import com.foodapp.helper.helper
import com.foodapp.view.main.DashBoard
import com.foodapp.view.main.food_payment
import com.foodapp.view.main.seller_review


class myFoodAdapter(private val dataList: List<ItemMyFood>, val supportFragmentManager : FragmentManager, private val res : Int) : RecyclerView.Adapter<myFoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myFoodAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
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

        fun bind(itemfood: ItemMyFood) {
            nameFood.text = itemfood.name;
            tag.text = itemfood.tag;
            rating.text = itemfood.star.toString();
            review.text = itemfood.review;
            price.text = itemfood.price.toString();
            img?.let { imageView ->
                helper.ShowImageUrl(
                    itemfood.imageUrl,
                    imageView
                )
            }
            itemView.setOnClickListener {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.seller_page_your_fragment_id, seller_review())
                    .commit()
            }
        }
    }
}
