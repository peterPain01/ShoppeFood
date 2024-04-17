package com.foodapp.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.utils.FakeData
import com.foodapp.view.adapter.AddressAdapter

class UserAddress : AppCompatActivity() {
    private lateinit var addrList: RecyclerView
    private lateinit var backBtn: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_address)
    }

    override fun onStart() {
        super.onStart()
        addrList = findViewById<RecyclerView>(R.id.activity_user_address_addr_list)
        addrList.layoutManager = LinearLayoutManager(this)
        addrList.adapter = AddressAdapter(FakeData.createAddresses(), R.layout.item_address)

        backBtn = findViewById<View>(R.id.activity_user_address_back_btn)
        backBtn.setOnClickListener {
            this.finish()
        }
    }
}