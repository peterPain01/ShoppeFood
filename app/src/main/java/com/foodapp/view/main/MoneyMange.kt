package com.foodapp.view.main

import android.os.Bundle
import android.util.Log
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.ActivityMoneyManageBinding
import com.foodapp.viewmodel.CreateProductViewModel
import com.foodapp.viewmodel.MoneyManageViewModel

class MoneyMange : AppCompatActivity() {
    private lateinit var binding: ActivityMoneyManageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_money_manage)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_money_manage)
        binding.lifecycleOwner = this
        binding.fullname = intent.getStringExtra("fullname") ?: ""
        binding.viewModel = MoneyManageViewModel(SessionManager(this)) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            Log.d("FOODAPP:MoneyManage", it)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.activityMoneyManageBackBtn.setOnClickListener { this.finish() }
    }
}