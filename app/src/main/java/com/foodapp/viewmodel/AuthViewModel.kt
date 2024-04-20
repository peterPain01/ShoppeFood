package com.foodapp.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import com.foodapp.data.model.User
import com.foodapp.data.model.auth.AuthResponse
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import com.foodapp.utils.RegexUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthViewModel(
    private val sessionManager: SessionManager,
    private val userRepository: UserRepository,
    ) : ViewModel()
{
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
        val call: Call<AuthResponse> = userRepository.signUp(user)

        call.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                try {
                    if (response.isSuccessful || response.code() == 200) {
                        val signUpResponse = response.body()
                        if (signUpResponse != null) {
                            sessionManager.saveAuthToken(signUpResponse.tokens, signUpResponse.user.id);
                        } else {
                            Log.e(TAG, "Sign up response body is null")
                        }
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

    fun login(callback : (Boolean, String) -> Unit)
    {
        val user = User("", email, password)
        val call : Call<AuthResponse> = userRepository.logIn(user)

        call.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if(response.isSuccessful || response.code() == 200)
                {
                    val loginResponse = response.body()
                    if(loginResponse != null)
                    {
                        sessionManager.saveAuthToken(loginResponse.tokens, loginResponse.user.id);
                        Log.i("DEBUGGER", loginResponse.tokens.accessToken)
                    }
                    return callback(true, "Thành công rồi ⭐")
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

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                return  callback(false, "Network Error ! Please try again")
            }
        })
    }

}