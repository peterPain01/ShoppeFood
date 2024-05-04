package com.foodapp.view.main

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.foodapp.R

class AddPayment : AppCompatActivity() {
    private lateinit var backBtn: ImageView

    // it will save the last method button user click
    private lateinit var trackingMethod: ImageView
    private lateinit var zalopayMethod: ImageView
    private lateinit var vnpayMethod: ImageView
    private lateinit var visaMethod: ImageView
    private lateinit var mastercardMethod: ImageView
    private lateinit var paymentConfirm: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_payment)
    }

    override fun onStart() {
        super.onStart()
        backBtn = findViewById(R.id.add_payment_backBtn)
        zalopayMethod = findViewById(R.id.add_payment_zalopay)
        vnpayMethod = findViewById(R.id.add_payment_vnpay)
        visaMethod = findViewById(R.id.add_payment_visa)
        mastercardMethod = findViewById(R.id.add_payment_mastercard)
        paymentConfirm = findViewById(R.id.add_payment_confirm)

        trackingMethod = zalopayMethod
        initEvent()
    }


    fun initEvent() {
        backBtn.setOnClickListener {
            this.finish()
        }
        zalopayMethod.setOnClickListener {
            inActiveMethod(trackingMethod)
            trackingMethod = zalopayMethod
            activeMethod(trackingMethod)
        }

        vnpayMethod.setOnClickListener {
            inActiveMethod(trackingMethod)
            trackingMethod = vnpayMethod
            activeMethod(trackingMethod)
        }

        visaMethod.setOnClickListener {
            inActiveMethod(trackingMethod)
            trackingMethod = visaMethod
            activeMethod(trackingMethod)
        }
        mastercardMethod.setOnClickListener {
            inActiveMethod(trackingMethod)
            trackingMethod = mastercardMethod
            activeMethod(trackingMethod)
        }

        paymentConfirm.setOnClickListener{
            val resultIntent = Intent()
            var methodInString : String = "cash"
            if(trackingMethod.id == zalopayMethod.id)
                methodInString = "zalopay"
            if(trackingMethod.id == vnpayMethod.id)
                methodInString = "vnpay"
            if(trackingMethod.id == visaMethod.id)
                methodInString = "visa"
            if(trackingMethod.id == mastercardMethod.id)
                methodInString = "mastercard"
            resultIntent.putExtra("paymentMethod", methodInString)

            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }


    fun transitionBackground(imageView: ImageView, newDrawableId: Int, duration: Int) {
        val currentDrawable = imageView.background
        val newDrawable = imageView.resources.getDrawable(newDrawableId)
        val crossfadeDrawable = TransitionDrawable(arrayOf(currentDrawable, newDrawable))
        imageView.background = crossfadeDrawable
        crossfadeDrawable.startTransition(duration)
    }
    fun activeMethod(imageView: ImageView) {
        transitionBackground(imageView, R.drawable.sffff7621sw2cr9_619999885559082, 320)
    }

    fun inActiveMethod(imageView: ImageView) {
        transitionBackground(imageView, R.color.grey_200, 320)
    }

}