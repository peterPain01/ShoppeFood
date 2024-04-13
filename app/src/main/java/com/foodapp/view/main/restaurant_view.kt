package com.foodapp.view.main

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.helper.helper
import com.foodapp.utils.FakeData
import com.foodapp.view.adapter.GridAdapter
import com.foodapp.view.Dialog_fragment.filter_restaurant_view
import kotlin.math.roundToInt
class restaurant_view : AppCompatActivity() {
    var avatarFood : ImageView?= null;
    var nameFood : TextView?= null;
    var detailFood : TextView ?= null;
    var ratingFood : TextView ?= null;
    var timePrepare : TextView ?= null;
    var btn_back : ImageButton?= null;
    var btn_view_more : ImageButton?= null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_view)
        init()
        LoadData()
        AddButton()
        HandleShowMoreDialogFragment()
    }

    override fun onStart() {
        super.onStart()
        val dummyList = FakeData.createDummyData()

        val gridView = findViewById<RecyclerView>(R.id.restaurant_view_gridView)
        gridView.layoutManager = GridLayoutManager(this, 2)
        val adapte_grid = GridAdapter(dummyList, R.layout.item_grid_checkout)
        gridView.adapter = adapte_grid
        btn_back?.setOnClickListener{
            finish();
        }
    }
    private fun init() {
        avatarFood = findViewById<ImageView>(R.id.Restaurant_view_BackGroundFood)
        nameFood = findViewById<TextView>(R.id.Restaurant_view_NameFood)
        detailFood = findViewById<TextView>(R.id.Restaurant_view_DetailFood)
        ratingFood = findViewById<TextView>(R.id.Restaurant_view_Rating)
        timePrepare = findViewById<TextView>(R.id.Restaurant_view_TimePrepare)
        btn_back = findViewById<ImageButton>(R.id.Restaurant_view_btn_back)
        btn_view_more = findViewById<ImageButton>(R.id.Restaurant_view_btn_option)
    }
    private fun LoadData() {
        avatarFood?.let { imageView ->
            helper.ShowImageUrl(
                "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg",
                imageView
            )
        }
        nameFood?.text = "Pizza calzone european"
        detailFood?.text = "Prosciutto e funghi is a pizza variety that is topped with tomato sauce."
        ratingFood?.text = "4.7"
        timePrepare?.text = "20 min"
    }
    private fun HandleShowMoreDialogFragment() {
        btn_view_more?.setOnClickListener {
            val dialogFragment = filter_restaurant_view()
            dialogFragment.show(supportFragmentManager, "CustomDialogFragment")
        }
    }
    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).roundToInt()
    }
    private fun AddButton(){
        val linearLayout = findViewById<LinearLayout>(R.id.Restaurant_view_LinearLayout)
        val buttonCount = 5

        for (i in 1..buttonCount) {
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                dpToPx(46)
            ).apply {
                marginStart = dpToPx(20)
            }
            button.background = ContextCompat.getDrawable(this, R.drawable.detail_page_btn_radius)
            button.text = "Pizza"
            button.setTextColor(ContextCompat.getColor(this, R.color.black))
            button.setOnClickListener {
                // Button click action
            }
            linearLayout.addView(button)
        }
    }
}