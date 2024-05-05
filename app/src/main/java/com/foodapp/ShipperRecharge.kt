package com.foodapp

import ApiService
import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.content.ContextCompat
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.User
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Response

class ShipperRecharge : AppCompatActivity() {
    private lateinit var userService : ApiService
    private lateinit var button_20 : Button
    private lateinit var button_50 : Button
    private lateinit var button_100 : Button
    private lateinit var button_200 : Button
    private lateinit var button_500 : Button
    private lateinit var button_placeOrder : Button
    private lateinit var tracking_button : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipper_recharge)
    }

    override fun onStart() {
        super.onStart()
        button_20 = findViewById(R.id.shipper_recharge_20)
        button_50 = findViewById(R.id.shipper_recharge_50)
        button_100 = findViewById(R.id.shipper_recharge_100)
        button_200 = findViewById(R.id.shipper_recharge_200)
        button_500 = findViewById(R.id.shipper_recharge_500)
        tracking_button = button_500
        button_placeOrder = findViewById(R.id.shipper_recharge_rechargeButton)
        userService =  UserRepository(SessionManager(this)).create(ApiService::class.java)
        button_20.setOnClickListener{
            inActive(tracking_button)
            tracking_button = button_20
            active(tracking_button)
        }
        button_50.setOnClickListener{
            inActive(tracking_button)
            tracking_button = button_50
            active(tracking_button)
        }
        button_100.setOnClickListener{
            inActive(tracking_button)
            tracking_button = button_100
            active(tracking_button)
        }
        button_200.setOnClickListener{
            inActive(tracking_button)
            tracking_button = button_200
            active(tracking_button)
        }
        button_500.setOnClickListener{
            inActive(tracking_button)
            tracking_button = button_500
            active(tracking_button)
        }

        button_placeOrder.setOnClickListener{

        }
    }

    private fun inActive(button : Button)
    {
        button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.white))
    }
    private fun active(button : Button)
    {
        button.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.orange))
    }

    private fun CallAPI (){
        userService.getCurrentUserInfo().enqueue(object: retrofit2.Callback<ApiResult<User>> {
            override fun onResponse(
                call: Call<ApiResult<User>>,
                response: Response<ApiResult<User>>
            ) {
                val body = response.body()
                if (response.code() == 200) {
//                    user.value = body?.metadata
                } else {
                    Log.i("FEATURE", body?.message.toString())
                }
            }
            override fun onFailure(call: Call<ApiResult<User>>, t: Throwable) {
                Log.i("FEATURE", t.message.toString())
            }
        })
    }
}