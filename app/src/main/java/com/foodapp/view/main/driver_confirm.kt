package com.foodapp.view.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.ActivityDriverConfirmBinding
import com.foodapp.view.Dialog_fragment.FragmentDetailOrder
import com.foodapp.viewmodel.DriverConfirmViewModel

class driver_confirm : AppCompatActivity() {
    private lateinit var binding: ActivityDriverConfirmBinding
    private lateinit var orderId: String
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
        binding.viewModel = DriverConfirmViewModel(orderId, SessionManager(this))
    }

    override fun onStart() {
        super.onStart()
        replaceFragment(FragmentDetailOrder(orderId))
    }
    private fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.activity_driver_confirm_fragment_container, fragment)
        fragmentTransaction.commit()
    }
}
