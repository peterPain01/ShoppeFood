package com.foodapp.view.main

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.foodapp.R
import com.foodapp.helper.helper
import android.content.res.ColorStateList
import android.widget.ImageButton


class food_payment : AppCompatActivity() {
    var btn_size1 : Button? = null;
    var btn_size2 : Button? = null;
    var btn_size3 : Button? = null;
    var avatarFood: ImageView?= null;
    var nameFood: TextView ?= null;
    var detailFood: TextView ?= null;
    var ratingFood: TextView ?= null;
    var timePrepare: TextView?= null;
    var btn_back: ImageButton ?= null;
    var preActiveSize: Int = 0;
    var ListBtnSize = arrayListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_payment)
        init();
        LoadData();
        HandleSize();
    }

    private fun init() {
        avatarFood = findViewById<ImageView>(R.id.Food_payment_BackGroundFood)
        nameFood = findViewById<TextView>(R.id.Food_payment_NameFood)
        detailFood = findViewById<TextView>(R.id.Food_payment_DetailFood)
        ratingFood = findViewById<TextView>(R.id.Food_payment_Rating)
        timePrepare = findViewById<TextView>(R.id.Food_payment_TimePrepare)
        btn_size1 = findViewById<Button>(R.id.Food_payment_Size1)
        btn_size2 = findViewById<Button>(R.id.Food_payment_Size2)
        btn_size3 = findViewById<Button>(R.id.Food_payment_Size3)
        btn_size1?.let { ListBtnSize.add(it) }
        btn_size2?.let { ListBtnSize.add(it) }
        btn_size3?.let { ListBtnSize.add(it) }
        btn_back = findViewById<ImageButton>(R.id.Food_payment_btn_back)
    }
    private fun LoadData() {

        // default active image
        btn_size1?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FE6203"))
        nameFood?.text = "Pizza calzone european"
        detailFood?.text = "Prosciutto e funghi is a pizza variety that is topped with tomato sauce."
        avatarFood?.let { imageView ->
            helper.ShowImageUrl(
                "https://images.pexels.com/photos/1640777/pexels-photo-1640777.jpeg",
                imageView
            )
        }
        ratingFood?.text = "4.7"
        timePrepare?.text = "20 min"
    }
    private fun HandleSize() {
        btn_size1?.setOnClickListener {
            SupportHanldeSize(0, preActiveSize);
        }
        btn_size2?.setOnClickListener {
            SupportHanldeSize(1, preActiveSize);
        }
        btn_size3?.setOnClickListener {
            SupportHanldeSize(2, preActiveSize);
        }
    }
    private fun SupportHanldeSize(idShow: Int, preShow: Int){
        ListBtnSize.get(preShow)?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F0F5FA"))
        preActiveSize = idShow;
        ListBtnSize.get(idShow)?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FE6203"))
    }
}