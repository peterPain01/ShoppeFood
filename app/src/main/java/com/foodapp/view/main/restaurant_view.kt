package com.foodapp.view.main

import ApiService
import android.content.Context
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.LikeAPI
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.databinding.ActivityRestaurantViewBinding
import com.foodapp.helper.helper
import com.foodapp.utils.FakeData
import com.foodapp.view.Dialog_fragment.filter_restaurant_view
import com.foodapp.viewmodel.RestaurantViewModel
import retrofit2.Call
import retrofit2.Response

class restaurant_view : AppCompatActivity() {
    lateinit var binding: ActivityRestaurantViewBinding
    val service = RetrofitClient.retrofit.create(ApiService::class.java)
    lateinit var likeAPI : LikeAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_view)
        val shopId = intent.getStringExtra("shopId")!!
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_view)
        binding.lifecycleOwner = this
        binding.viewModel = RestaurantViewModel(shopId, {
            Toast.makeText(this, it ?: "", Toast.LENGTH_LONG).show()
        }, {
            helper.ShowImageUrl(it, binding.RestaurantViewBackGroundFood)
        })
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
        var count : Int = 1
        val isUserLikedShop = binding.viewModel?.shop?.value?.isUserLiked
        val iconDrawable = ContextCompat.getDrawable(this, R.drawable.ic_heart_solid)
        Log.i("LIKE", isUserLikedShop.toString())

        if(isUserLikedShop == true)
        {
            iconDrawable?.setColorFilter(ContextCompat.getColor(this, R.color.orange), PorterDuff.Mode.SRC_IN)
            count = 2
        }
        else{
            iconDrawable?.setColorFilter(ContextCompat.getColor(this, R.color.grey_500), PorterDuff.Mode.SRC_IN)
        }

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