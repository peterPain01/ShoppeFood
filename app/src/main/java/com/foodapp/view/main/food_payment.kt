package com.foodapp.view.main

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.Review
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.ActivityFoodPaymentBinding
import com.foodapp.viewmodel.FoodPaymentViewModel
import java.text.SimpleDateFormat
import java.util.Date


class food_payment : AppCompatActivity() {
    private lateinit var binding: ActivityFoodPaymentBinding;
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    var preActiveSize: Int = 0;
    var ListBtnSize = arrayListOf<Button>()
    private lateinit var productId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_payment)
        productId = intent.getStringExtra("productId")!!
        binding = DataBindingUtil.setContentView(this, R.layout.activity_food_payment);
        binding.lifecycleOwner = this
        binding.FoodPaymentCommentRcv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.viewModel = FoodPaymentViewModel(productId, SessionManager(this)) {
            Toast.makeText(this, it ?: "", Toast.LENGTH_LONG).show()
        }
        binding.viewModel?.product?.observe(this) {
            Glide.with(this)
                .load(it.product_thumb)
                .into(binding.FoodPaymentBackGroundFood)
        }
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data
            val review = data?.getSerializableExtra("review", Review::class.java)
            review?.let {
                it.productId = productId
                it.comment_date = SimpleDateFormat("dd/MM/YYYY").format(Date())
                binding.viewModel?.addComment(it)
            }
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
        binding.activityFoodPaymentAddCommentBtn.setOnClickListener {
            val newIntent = Intent(this, AddFoodComment::class.java)
            resultLauncher.launch(newIntent)
        }
    }

}