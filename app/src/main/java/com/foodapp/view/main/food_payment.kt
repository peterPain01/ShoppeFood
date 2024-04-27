package com.foodapp.view.main

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.ActivityFoodPaymentBinding
import com.foodapp.viewmodel.FoodPaymentViewModel


class food_payment : AppCompatActivity() {
    private lateinit var binding: ActivityFoodPaymentBinding;
    var preActiveSize: Int = 0;
    var ListBtnSize = arrayListOf<Button>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_payment)
        val id = intent.getStringExtra("productId")!!
        binding = DataBindingUtil.setContentView(this, R.layout.activity_food_payment);
        binding.lifecycleOwner = this
        binding.viewModel = FoodPaymentViewModel(id, {
            Toast.makeText(this, it ?: "", Toast.LENGTH_LONG).show()
        }, {
            Glide.with(this)
                .load(it)
                .into(binding.FoodPaymentBackGroundFood)
        }, SessionManager(this))
        init();
        SupportHanldeSize(0, preActiveSize);
    }
    private fun init() {
        val btn_size1 = binding.FoodPaymentSize1
        val btn_size2 = binding.FoodPaymentSize2
        val btn_size3 = binding.FoodPaymentSize3
        btn_size1.setOnClickListener { SupportHanldeSize(0, preActiveSize); }
        btn_size2.setOnClickListener { SupportHanldeSize(1, preActiveSize); }
        btn_size3.setOnClickListener { SupportHanldeSize(2, preActiveSize); }
        btn_size1.let { ListBtnSize.add(it) }
        btn_size2.let { ListBtnSize.add(it) }
        btn_size3.let { ListBtnSize.add(it) }
        binding.FoodPaymentBtnBack.setOnClickListener { this.finish() }
        binding.FoodPaymentIncreaseBtn.setOnClickListener { binding.viewModel?.incCount() }
        binding.FoodPaymentDecreaseBtn.setOnClickListener { binding.viewModel?.decCount() }
        binding.foodPaymentOriginalPrice.paintFlags = binding.foodPaymentOriginalPrice.paintFlags.or(Paint.STRIKE_THRU_TEXT_FLAG)
        binding.FoodPaymentAddToCard.setOnClickListener {
            binding.viewModel!!.addToCart {
                this.finish()
            }
        }
    }
    private fun SupportHanldeSize(idShow: Int, preShow: Int){
        ListBtnSize.get(preShow)?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F0F5FA"))
        preActiveSize = idShow;
        ListBtnSize.get(idShow)?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FE6203"))
    }
}