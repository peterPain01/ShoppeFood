package com.foodapp.view.main

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.ActivityFoodPaymentBinding
import com.foodapp.view.adapter.UserComment.UserCommentAdapter
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
        binding.FoodPaymentCommentRcv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.viewModel = FoodPaymentViewModel(id, SessionManager(this)) {
            Toast.makeText(this, it ?: "", Toast.LENGTH_LONG).show()
        }
        binding.viewModel?.product?.observe(this) {
            Glide.with(this)
                .load(it.product_thumb)
                .into(binding.FoodPaymentBackGroundFood)
        }

        // set adapter
        init();
    }

    private fun init() {
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

}