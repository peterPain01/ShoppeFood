package com.foodapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.Review
import com.foodapp.helper.helper

class reviewAdapter(private val dataList: List<Review>, private val res : Int) : RecyclerView.Adapter<reviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): reviewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: reviewAdapter.ViewHolder, position: Int) {
        val data = dataList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.item_review_img)
        val date = itemView.findViewById<TextView>(R.id.item_review_textView6)
        val title = itemView.findViewById<TextView>(R.id.item_review_textView7)
        val content = itemView.findViewById<TextView>(R.id.item_view_content)

        val star1 = itemView.findViewById<ImageView>(R.id.item_review_star1)
        val star2 = itemView.findViewById<ImageView>(R.id.item_review_star2)
        val star3 = itemView.findViewById<ImageView>(R.id.item_review_star3)
        val star4 = itemView.findViewById<ImageView>(R.id.item_review_star4)
        val star5 = itemView.findViewById<ImageView>(R.id.item_review_star5)
        fun handelStar(star: Int) {
            val starsList = listOf(star1, star2, star3, star4, star5)
            for(i in 1..star){
                starsList[i - 1].setImageResource(R.drawable.ic_star_primary)
            }
            for(i in star + 1 .. 5){
                starsList[i - 1].setImageResource(R.drawable.ic_star)
            }
        }
        fun bind(data: Review) {
            img?.let { imageView ->
                helper.ShowImageUrl(
                    data.comment_content_image,
                    imageView
                )
            }
            date.text = data.comment_date
            title.text = data.comment_title
            content.text = data.comment_content_text
            handelStar(data.comment_star.toInt())
        }
    }
}