package com.foodapp.view.main

import android.content.Intent
import android.graphics.Paint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.User
import com.foodapp.databinding.ActivityPersonalInfoBinding

class PersonalInfo() : AppCompatActivity() {
    private lateinit var binding: ActivityPersonalInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_personal_info)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_personal_info)
        binding.info = intent.getSerializableExtra("info", User::class.java)
        binding.lifecycleOwner = this
        Glide.with(this)
            .load(binding.info?.avatar)
            .into(binding.activityPersonalInfoImage)
    }

    override fun onStart() {
        super.onStart()
        val editBtn = binding.activityPersonalInfoEditButton
        editBtn.paintFlags = editBtn.paintFlags.or(Paint.UNDERLINE_TEXT_FLAG)
        editBtn.setOnClickListener {
            val new_intent = Intent(this, EditUserInfo::class.java)
            new_intent.putExtra("info", binding.info)
            startActivity(new_intent)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            binding.info = intent.getSerializableExtra("info", User::class.java)
        } else {
            throw Exception("VERSION ERROR")
        }
    }

    fun onClickedBack(view: View) {
        finish()
    }
}