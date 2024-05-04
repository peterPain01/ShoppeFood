package com.foodapp.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.foodapp.R
import com.foodapp.data.model.UserAddress
import com.foodapp.databinding.ActivityDriverConfirmBinding
import com.foodapp.view.Dialog_fragment.FragmentDetailOrder

class driver_confirm : AppCompatActivity() {
    private lateinit var binding: ActivityDriverConfirmBinding
    private lateinit var orderId: String
    private var userAddress: UserAddress? = null
    private var shopAddress: UserAddress? = null
    private var price: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_confirm)
        val id = intent.getStringExtra("orderId")
        if (id == null) {
            Log.e("FOODAPP:driver_home", "Missing order id")
            throw Exception("Missing order id")
        }
        orderId = id
        binding = DataBindingUtil.setContentView(this, R.layout.activity_driver_confirm)
        binding.lifecycleOwner = this
    }

    override fun onStart() {
        super.onStart()
        val fragment = FragmentDetailOrder(orderId) {
            userAddress = it.order_user.address
            shopAddress = it.order_shop.address
            price = it.order_totalPrice
        }
        replaceFragment(fragment)


        binding.activityDriverConfirmBtn.setOnClickListener {
            val data = Intent()
            data.putExtra("userAddress", userAddress)
            data.putExtra("price", price)
            data.putExtra("shopAddress", shopAddress)
            setResult(RESULT_OK, data)
            finish()
        }
        binding.activityDriverConfirmCancelBtn.setOnClickListener {
            val data = Intent()
            setResult(RESULT_CANCELED, data)
            finish()
        }
    }
    private fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.activity_driver_confirm_fragment_container, fragment)
        fragmentTransaction.commit()
    }
}
