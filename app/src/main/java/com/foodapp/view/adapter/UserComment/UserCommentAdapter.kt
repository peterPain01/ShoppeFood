package com.foodapp.view.adapter.UserComment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.Review
import com.foodapp.data.model.UserAddress

class UserCommentAdapter (val dataList : MutableList<Review>): RecyclerView.Adapter<UserCommentAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        private val userImage : ImageView = itemView.findViewById(R.id.item_review_img)
        private val item_review_date : TextView = itemView.findViewById(R.id.item_review_textView6)
        private val item_review_title : TextView = itemView.findViewById(R.id.item_review_textView7)
        // rating
        private val item_review_content : TextView = itemView.findViewById(R.id.item_view_content)
        private val item_review_content_image : ImageView = itemView.findViewById(R.id.item_review_content_image)
        private val starList = listOf(
            R.id.item_review_star1,
            R.id.item_review_star2,
            R.id.item_review_star3,
            R.id.item_review_star4,
            R.id.item_review_star5,
        )

        fun bind(data: Review) {
            item_review_title.text = data.comment_title
            item_review_date.text = data.comment_date
            item_review_content.text = data.comment_content_text
            Glide.with(itemView.context)
                .load(data.comment_userAvatar)
                .into(userImage)
            Glide.with(itemView.context)
                .load(data.comment_content_image)
                .into(item_review_content_image)
            for (i in 0..<data.comment_star) {
                val itemView = itemView.findViewById<ImageView>(starList[i])
                itemView.setImageResource(R.drawable.star)
            }
            for (i in data.comment_star..<starList.size) {
                val itemView = itemView.findViewById<ImageView>(starList[i])
                itemView.setImageResource(R.drawable.ic_star)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    fun add(rv: Review) {
        dataList.add(rv)
        notifyItemInserted(dataList.size)
    }
}