package com.foodapp.view.main

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.databinding.ActivityOrderBinding
import com.foodapp.helper.helper
import com.foodapp.viewmodel.CartViewModel


class Order : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)
        binding.lifecycleOwner = this
        binding.viewModel = CartViewModel(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }, {
            helper.displayErrorPopup(this, it) {
                this.finish()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        binding.activityOrderNote.setOnKeyListener { view, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.viewModel?.updateNote()
                view.clearFocus()
                // hide virtual keyboard
                (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }
    fun onBackClicked(view: View) {
        this.finish()
    }
}