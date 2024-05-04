package com.foodapp.view.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.databinding.ActivityOrderBinding
import com.foodapp.helper.helper
import com.foodapp.viewmodel.CartViewModel


class Order : AppCompatActivity() {
    private lateinit var binding: ActivityOrderBinding
    private lateinit var otherPayment: TextView
    private lateinit var orderCashMethod: Button
    private lateinit var orderOtherMethod: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order)
        binding.lifecycleOwner = this
        binding.viewModel = CartViewModel(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }, { str, type ->
            helper.displayPopup(this, str, type) {
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
                (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    view.windowToken,
                    0
                )
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
        otherPayment= findViewById(R.id.order_other_payment_method)
        orderCashMethod = findViewById(R.id.order_cash_method)
        orderOtherMethod = findViewById(R.id.order_sub_payment_method)
        initEvent()
    }

    fun onBackClicked(view: View) {
        this.finish()
    }

    fun initEvent() {
        val paymentActivityResultContract =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val data = result.data
                    binding.viewModel?.paymentMethod = data?.getStringExtra("paymentMethod").toString()
                    activeMethodButton()
                }
            }
        otherPayment.setOnClickListener {
            val intent = Intent(this, AddPayment::class.java)
            paymentActivityResultContract.launch(intent)
        }

        orderCashMethod.setOnClickListener{
            activeButton(orderCashMethod)
            inActive(orderOtherMethod)
            binding.viewModel?.paymentMethod  = "cash"
        }
        orderOtherMethod.setOnClickListener{
            activeButton(orderOtherMethod)
            inActive((orderCashMethod))
            binding.viewModel?.paymentMethod  = orderOtherMethod.text.toString()
        }
    }


    private fun activeMethodButton() {
        if (binding.viewModel?.paymentMethod  != "cash") {
            orderOtherMethod.text = binding.viewModel?.paymentMethod
            activeButton(orderOtherMethod)
            inActive(orderCashMethod)
        }
    }

    private fun activeButton(button : Button)
    {
        button.setBackgroundResource(R.color.orange)
        button.setTextColor(resources.getColor(R.color.white))
    }

    private  fun inActive(button : Button)
    {
        button.setBackgroundResource(R.drawable.button_border_not_focus)
        button.setTextColor(resources.getColor(R.color.black))
    }

}

// cho phep thay doi khi nhan nut
//place order