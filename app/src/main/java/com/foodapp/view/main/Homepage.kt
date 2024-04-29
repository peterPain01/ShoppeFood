package com.foodapp.view.main

import ApiService
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.foodapp.R
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.databinding.ActivityHomepageBinding
import com.foodapp.view.Dialog_fragment.Fragment_Home
import com.foodapp.view.Dialog_fragment.Fragment_Likes
import com.foodapp.view.Dialog_fragment.UserInfoFragment


class Homepage : AppCompatActivity() {
    val service = RetrofitClient.retrofit.create(ApiService::class.java)
//    // 1.
    lateinit var binding : ActivityHomepageBinding

    override fun onStart() {
        super.onStart()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Fragment_Home())
        binding.homepageBottomNavigation.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.menu_bottom_home -> {
                    replaceFragment(Fragment_Home())
                    return@setOnItemSelectedListener true
                }
                R.id.menu_bottom_likes -> {
                    replaceFragment(Fragment_Likes())
                    return@setOnItemSelectedListener true
                }
                R.id.menu_bottom_me -> {
                    replaceFragment(UserInfoFragment())
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

    }

    fun replaceFragment(fragment: Fragment)
    {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.homepage_fragment_container, fragment)
        fragmentTransaction.commit()
    }
}