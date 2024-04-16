package com.foodapp.view.main

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.foodapp.R
import okhttp3.internal.notify

class seller_page : AppCompatActivity() {
    var btnDashBoard : ImageView ?= null;
    var btnMyFood : ImageView ?= null;
    var btnNotify : ImageView ?= null;
    var btnSetting : ImageView ?= null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seller_page)
    }

    override fun onStart() {
        super.onStart()
        btnDashBoard = findViewById(R.id.seller_page_dashboard)
        btnMyFood = findViewById(R.id.seller_page_my_food)
        btnNotify = findViewById(R.id.seller_page_notify)
        btnSetting = findViewById(R.id.seller_page_setting)
        var temp : ImageView ?= null;
        var value_temp: Int ?= null;

        supportFragmentManager.beginTransaction()
            .replace(R.id.seller_page_your_fragment_id, DashBoard())
            .commit()

        temp = btnDashBoard;
        value_temp = R.drawable.ic_more_detail;

        btnDashBoard?.setOnClickListener {
            if (value_temp != null) {
                temp?.setImageResource(value_temp!!)
            };
            temp = btnDashBoard;
            value_temp = R.drawable.ic_more_detail;
            btnDashBoard?.setImageResource(R.drawable.ic_more_detail_primary);

            supportFragmentManager.beginTransaction()
                .replace(R.id.seller_page_your_fragment_id, DashBoard())
                .commit()
        }
        btnMyFood?.setOnClickListener {
            if (value_temp != null) {
                temp?.setImageResource(value_temp!!)
            };
            temp = btnMyFood;
            value_temp = R.drawable.ic_detail;
            btnMyFood?.setImageResource(R.drawable.ic_detail_primary);
            supportFragmentManager.beginTransaction()
                .replace(R.id.seller_page_your_fragment_id, my_food(supportFragmentManager))
                .commit()
        }
        btnNotify?.setOnClickListener {
            if (value_temp != null) {
                temp?.setImageResource(value_temp!!)
            };
            temp = btnNotify;
            value_temp = R.drawable.ic_notify;
            btnNotify?.setImageResource(R.drawable.ic_notify_primary);
            supportFragmentManager.beginTransaction()
                .replace(R.id.seller_page_your_fragment_id, Seller_Notifications())
                .commit()
        }
        btnSetting?.setOnClickListener {
            if (value_temp != null) {
                temp?.setImageResource(value_temp!!)
            };
            temp = btnSetting;
            value_temp = R.drawable.ic_human;
            btnSetting?.setImageResource(R.drawable.ic_human_primary);
        }

    }
}