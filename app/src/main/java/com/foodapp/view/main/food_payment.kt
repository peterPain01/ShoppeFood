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
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.foodapp.databinding.ActivityFoodPaymentBinding
import com.foodapp.utils.FakeData
import com.foodapp.view.adapter.GridAdapter
import com.foodapp.viewmodel.FoodPaymentViewModel


class food_payment : AppCompatActivity() {
    private lateinit var binding: ActivityFoodPaymentBinding;
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
        val id = intent.getStringExtra("productId")!!
        binding = DataBindingUtil.setContentView(this, R.layout.activity_food_payment)
        binding.viewModel = FoodPaymentViewModel(id, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }, {
            val temp = Glide.with(this)
                .load(it)
                .into(binding.FoodPaymentBackGroundFood)
        })
        init();
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