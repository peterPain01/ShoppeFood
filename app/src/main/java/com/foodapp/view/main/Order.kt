package com.foodapp.view.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.databinding.ActivityOrderBinding
import com.foodapp.viewmodel.CartViewModel

class Order : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)
        binding.lifecycleOwner = this
        binding.viewModel = CartViewModel(this) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
    fun onBackClicked(view: View) {
        this.finish()
    }
}