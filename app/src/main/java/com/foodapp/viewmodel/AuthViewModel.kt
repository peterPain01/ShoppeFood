package com.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.foodapp.data.model.User
import com.foodapp.data.repository.UserRepository
import com.foodapp.utils.RegexUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    var email: String = ""
    var password: String = ""
    var confirm_password: String = ""

    fun signUp(callback: (Boolean, String) -> Unit) {
        if(!RegexUtils.isValidEmail(email))
        {
            return callback(false, "Please give us valid email")
        }
        if(password.length < 6)
        {
            return callback(false, "Password must have at least 6 characters")
        }
        val user = User("", email, password)
        val call: Call<User> = userRepository.signUp(user)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                try {
                    if (response.isSuccessful || response.code() == 200) {
                        Log.i("DEBUG", "")
                        callback(true, "Account successfully created")
                    } else if (response.code() == 409) {
                        callback(false, "Email existed, please try again")
                    } else if (response.code() == 400) {
                        callback(false, "Missing some properties")
                    } else {
                        callback(false, "Network Error ! Try again")
                    }
                } catch (e: Exception) {
                    Log.i("DB", "Error occured white signup: " + e.message.toString())
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                try {
                    Log.i("DEBUGG", t.message.toString())
                    callback(false, "Network Error ! Try again")
                } catch (e: Exception) {
                    Log.i("DEBUGG", e.message.toString())
                }
            }
        })
    }

    fun login(callback : (Boolean, String) -> Unit)
    {
        val user = User("", email, password)
        val call : Call<User> = userRepository.logIn(user)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.isSuccessful || response.code() == 200)
                {
                    return callback(true, response.body().toString())
                }
                else if(response.code() == 400){
                    return  callback(false, "Missing one of password/email")
                }
                else if(response.code() == 401){
                    return  callback(false, "Invalid email or password")
                }
                else{
                    return  callback(false, "Network Error ! Please try again")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                return  callback(false, "Network Error ! Please try again")
            }
        })
    }

}