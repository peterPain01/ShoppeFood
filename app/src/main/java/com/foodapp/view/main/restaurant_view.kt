package com.foodapp.view.main

import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.LikeAPI
import com.foodapp.databinding.ActivityRestaurantViewBinding
import com.foodapp.view.Dialog_fragment.filter_restaurant_view
import com.foodapp.viewmodel.RestaurantViewModel

class restaurant_view : AppCompatActivity() {
    lateinit var binding: ActivityRestaurantViewBinding
    lateinit var likeAPI : LikeAPI
    private var iconDrawable: Drawable? = null
    private var count : Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_view)
        val shopId = intent.getStringExtra("shopId")!!
        iconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_heart_solid)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_view)
        binding.lifecycleOwner = this
        binding.viewModel = RestaurantViewModel(shopId, SessionManager(this)) {
            Toast.makeText(this, it ?: "", Toast.LENGTH_LONG).show()
        }
        binding.viewModel?.shop?.observe(this) {
            if (it.isUserLiked) {
                iconDrawable?.setColorFilter(ContextCompat.getColor(this, R.color.orange), PorterDuff.Mode.SRC_IN)
                count = 2
            } else {
                iconDrawable?.setColorFilter(ContextCompat.getColor(this, R.color.grey_500), PorterDuff.Mode.SRC_IN)
            }
            Glide.with(this)
                .load(it.image)
                .into(binding.RestaurantViewBackGroundFood)
        }
        HandleShowMoreDialogFragment()
    }
    override fun onStart() {
        super.onStart()
        // popular recycler view
        binding.RestaurantViewPopularRCV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.restaurantViewGridView.layoutManager = GridLayoutManager(this, 2)

        binding.RestaurantViewBtnBack.setOnClickListener{
            finish();
        }
        likeAPI = LikeAPI(this)
        initLikeButton()
    }
    private fun HandleShowMoreDialogFragment() {
        binding.RestaurantViewBtnOption.setOnClickListener {
            val dialogFragment = filter_restaurant_view()
            dialogFragment.show(supportFragmentManager, "CustomDialogFragment")
        }
    }

    private fun initLikeButton()
    {
        binding.RestaurantViewShopLikeButton.setImageDrawable(iconDrawable)
        binding.RestaurantViewShopLikeButton.setOnClickListener {
            Log.i("COUNT", count.toString())
            if(count % 2 == 0)
            {
                likeAPI.postUnLikeToServer(this, binding.viewModel?.shop?.value?._id.toString())
                iconDrawable?.setColorFilter(ContextCompat.getColor(this, R.color.grey_500), PorterDuff.Mode.SRC_IN)
            }
            else{
                likeAPI.postLikeToServer(this, binding.viewModel?.shop?.value?._id.toString())
                iconDrawable?.setColorFilter(ContextCompat.getColor(this, R.color.orange), PorterDuff.Mode.SRC_IN)
            }
            binding.RestaurantViewShopLikeButton.setImageDrawable(iconDrawable)
            count++
        }
    }
}