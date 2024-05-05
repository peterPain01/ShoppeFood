package com.foodapp.viewmodel

import ApiService
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.User
import com.foodapp.data.model.auth.AuthResponse
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.data.repository.UserRepository
import com.foodapp.utils.RegexUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthViewModel(
    private val sessionManager: SessionManager,
    ) : ViewModel()
{
    private val service = RetrofitClient.retrofit.create(ApiService::class.java)
    var phone: String = ""
    var password: String = ""
    var confirm_password: String = ""

    fun signUp(callback: (Boolean, String) -> Unit) {
        if(!RegexUtils.isValidPhoneNumber(phone))
        {
            return callback(false, "Please give us valid phone number")
        }
        if(password.length < 6)
        {
            return callback(false, "Password must have at least 6 characters")
        }
        val user = User("", phone, password)
        val call: Call<AuthResponse> = service.signUp(user)

        call.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(
                call: Call<AuthResponse>,
                response: Response<AuthResponse>
            ) {
                try {
                    if (response.isSuccessful || response.code() == 201) {
                        val signUpResponse = response.body()
                        callback(true, "Account successfully created")
                    } else if (response.code() == 409) {
                        callback(false, "Phone number existed, please try again")
                    } else if (response.code() == 400) {
                        callback(false, "Missing some properties")
                    } else {
                        callback(false, "Network Error ! Try again")
                    }
                } catch (e: Exception) {
                Log.i("DB", "Error occured white signup: " + e.message.toString())
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                try {
                    Log.i("DEBUGG", t.message.toString())
                    callback(false, "Network Error ! Try again")
                } catch (e: Exception) {
                    Log.i("DEBUGG", e.message.toString())
                }
            }
        })
    }

    fun login(callback : (Boolean, User?, String) -> Unit)
    {
        val user = User("", phone, password)
        val call : Call<ApiResult<AuthResponse>> = service.logIn(user)

        call.enqueue(object : Callback<ApiResult<AuthResponse>> {
            override fun onResponse(
                call: Call<ApiResult<AuthResponse>>,
                response: Response<ApiResult<AuthResponse>>
            ) {
                if(response.isSuccessful || response.code() == 201)
                {
                    val body = response.body()
                    val loginResponse = body?.metadata
                    if(loginResponse != null)
                    {
                        sessionManager.saveAuthToken(loginResponse.tokens, loginResponse.user.id);
                        Log.i("DEBUGGER", loginResponse.tokens.accessToken)
                    }
                    return callback(true, loginResponse?.user, "Thành công rồi ⭐")
                }
                else if(response.code() == 400){
                    return  callback(false, null, "Missing one of password/phone number")
                }
                else if(response.code() == 401){
                    return  callback(false, null, "Invalid phone number or password")
                }
                else{
                    return  callback(false, null, "Network Error ! Please try again")
                }
            }

            override fun onFailure(call: Call<ApiResult<AuthResponse>>, t: Throwable) {
                return  callback(false, null, "Network Error ! Please try again")
            }
        })
    }

}