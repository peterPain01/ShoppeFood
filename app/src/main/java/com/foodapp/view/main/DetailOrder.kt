package com.foodapp.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.foodapp.R
import com.foodapp.view.Dialog_fragment.FragmentDetailOrder

class DetailOrder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_order)
    }

    override fun onStart() {
        super.onStart()
        val orderId = intent.getStringExtra("orderId")!!
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.activity_detail_order_frag, FragmentDetailOrder(orderId) {})
        fragmentTransaction.commit()

        findViewById<View>(R.id.activity_detail_order_back_btn).setOnClickListener {
            this.finish()
        }
    }
}