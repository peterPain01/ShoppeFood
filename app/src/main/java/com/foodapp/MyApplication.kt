package com.foodapp

import ApiService
import android.app.Application
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Response

class MyApplication: Application(){
    lateinit var sessionManager: SessionManager
    companion object {
        var isNew: Boolean = true
    }
    override fun onCreate() {
        super.onCreate()
        sessionManager = SessionManager(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(object: DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                super.onResume(owner)
                Log.d("FOODAPP:MyApplication", "resume")
                setState(true)
            }

            override fun onStop(owner: LifecycleOwner) {
                super.onStop(owner)
                Log.d("FOODAPP:MyApplication", "stop")
                setState(false)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                Log.d("FOODAPP:MyApplication", "destroy")
                setState(false)
                sessionManager.removeAuthToken()
                // super.onDestroy(owner)
            }
        })
    }
    fun setState(active: Boolean) {
        if (sessionManager.isLogin() && !isNew) {
            val state = if (active) "active" else "inactive"
            UserRepository(sessionManager).create(ApiService::class.java).setState(state).enqueue(object: retrofit2.Callback<ApiResult<Nothing>> {
                override fun onResponse(
                    call: Call<ApiResult<Nothing>>,
                    response: Response<ApiResult<Nothing>>
                ) {
                    if (!response.isSuccessful) {
                        Log.e("FOODAPP:MyApplication", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                    Log.e("FOODAPP:MyApplication", t.stackTraceToString())
                }
            })
        }
    }
}