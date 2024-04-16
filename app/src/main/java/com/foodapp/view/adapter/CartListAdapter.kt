package com.foodapp.view.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.DishItems
import kotlin.math.max

class CartListAdapter(private val dataList: MutableList<DishItems>, private val res : Int, private val updateTotal: () -> Unit) : RecyclerView.Adapter<CartListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position], position)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.cart_item_imageView3)
        private val name = itemView.findViewById<TextView>(R.id.cart_item_textView5)
        private val price = itemView.findViewById<TextView>(R.id.cart_item_textView6)
        private val count = itemView.findViewById<TextView>(R.id.cart_item_textView9)
        private val addButton = itemView.findViewById<ImageButton>(R.id.cart_item_add_button)
        private val minusButton = itemView.findViewById<ImageButton>(R.id.cart_item_minus_button)
        private val closeButton = itemView.findViewById<CardView>(R.id.cart_item_close_button)
        fun bind(data: DishItems, position: Int) {
            Glide.with(itemView.context)
                .load(data.dish.imageUrl)
                .into(image)
            name.text = data.dish.name
            price.text = String.format(null, "$%.2f", data.dish.price * data.count)
            count.text = data.count.toString()
            addButton.setOnClickListener {
                data.count = data.count + 1
                notifyItemChanged(position)
//                price.text = String.format(null, "$%.2f", data.dish.price * data.count)
//                count.text = data.count.toString()
                updateTotal()
            }
            minusButton.setOnClickListener {
                data.count = max(data.count - 1, 0)
                notifyItemChanged(position)
//                price.text = String.format(null, "$%.2f", data.dish.price * data.count)
//                count.text = data.count.toString()
                updateTotal()
            }
            closeButton.setOnClickListener {
                dataList.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, dataList.count())
                updateTotal()
            }
        }
    }

}