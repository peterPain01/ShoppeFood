package com.foodapp.data.model.auth

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.foodapp.MyApplication
import com.foodapp.R

class SessionManager(private val context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
        const val USER_ID = "user_id"
        const val ROLE = "role"
    }

    fun saveAuthToken(tokens: Tokens, user_id: String) {
        val editor = prefs.edit()
        editor.putString(ACCESS_TOKEN, tokens.accessToken)
        editor.putString(REFRESH_TOKEN, tokens.refreshToken)
        editor.putString(USER_ID, user_id)
        editor.apply()
        MyApplication.isNew = false
    }

    fun saveRole(role: String) {
        val editor = prefs.edit()
        editor.putString(ROLE, role)
        editor.apply()
    }
    fun getRole(): String {
        return prefs.getString(ROLE, null)!!
    }

    data class UserAuthInfo(val userId: String?, val accessToken: String?)

    fun fetchAuthToken(): UserAuthInfo {
        val accessToken = prefs.getString(ACCESS_TOKEN, null)
        val userId = prefs.getString(USER_ID, null)
        return UserAuthInfo(userId, accessToken)
    }

    fun removeAuthToken() {
        val editor = prefs.edit()
        editor.remove(ACCESS_TOKEN)
        editor.remove(REFRESH_TOKEN)
        editor.remove(USER_ID)
        editor.remove(ROLE)
        editor.apply()
    }

    fun isLogin(): Boolean {
        val userId = prefs.getString(USER_ID, null)
        Log.d("FOODAPP:Session", userId.toString())
        return userId != null
    }
}