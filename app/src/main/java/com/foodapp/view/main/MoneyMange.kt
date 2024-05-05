package com.foodapp.view.main

import android.os.Bundle
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.viewmodel.CreateProductViewModel

class MoneyMange : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_manage)
    }

    override fun onStart() {
        super.onStart()
    }
}