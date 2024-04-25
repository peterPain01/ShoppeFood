package com.foodapp.view.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.Category

class HorizontalAdapter(private val dataList: List<Category>, private val res : Int) : RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btn: Button = itemView.findViewById(R.id.item_horizontal_btn)
        private val image: ImageView = itemView.findViewById(R.id.item_horizontal_image)

        // binding Hinh anh
        fun bind(data: Category) {
            btn.text = data.name
            Glide.with(itemView.context)
                .load(data.image)
                .into(image)
        }
    }
}