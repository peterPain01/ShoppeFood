package com.foodapp.view.main

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.databinding.ActivityRestaurantViewBinding
import com.foodapp.helper.helper
import com.foodapp.utils.FakeData
import com.foodapp.view.adapter.GridAdapter
import com.foodapp.view.Dialog_fragment.filter_restaurant_view
import com.foodapp.viewmodel.RestaurantViewModel
import kotlin.math.roundToInt
class restaurant_view : AppCompatActivity() {
    lateinit var binding: ActivityRestaurantViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_view)
        val shopId = intent.getStringExtra("shopId")!!
        binding = DataBindingUtil.setContentView(this, R.layout.activity_restaurant_view)
        binding.lifecycleOwner = this
        binding.viewModel = RestaurantViewModel(shopId, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }, {
            helper.ShowImageUrl(it, binding.RestaurantViewBackGroundFood)
        })
        HandleShowMoreDialogFragment()
    }

    override fun onStart() {
        super.onStart()

        val dummyList = FakeData.createDummyData()
        binding.restaurantViewGridView.layoutManager = GridLayoutManager(this, 2)
        binding.RestaurantViewBtnBack.setOnClickListener{
            finish();
        }
    }
    private fun HandleShowMoreDialogFragment() {
        binding.RestaurantViewBtnOption.setOnClickListener {
            val dialogFragment = filter_restaurant_view()
            dialogFragment.show(supportFragmentManager, "CustomDialogFragment")
        }
    }
}