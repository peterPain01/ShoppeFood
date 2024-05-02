package com.foodapp.view.adapter.UserComment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.foodapp.R
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodapp.data.model.Category
import com.foodapp.data.model.Review
import com.foodapp.view.adapter.HorizontalAdapter
import java.text.SimpleDateFormat

class UserCommentAdapter (val dataList : List<Review>): RecyclerView.Adapter<UserCommentAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        private val userImage : ImageView = itemView.findViewById(R.id.item_review_img)
        private val item_review_date : TextView = itemView.findViewById(R.id.item_review_textView6)
        private val item_review_title : TextView = itemView.findViewById(R.id.item_review_textView7)
        // rating
        private val item_review_content : TextView = itemView.findViewById(R.id.item_view_content)
        private val item_review_content_image : ImageView = itemView.findViewById(R.id.item_review_content_image)

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

}