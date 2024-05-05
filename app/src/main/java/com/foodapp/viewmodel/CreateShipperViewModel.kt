package com.foodapp.viewmodel

import ApiService
import android.util.Log
import androidx.lifecycle.ViewModel
import com.foodapp.data.model.ApiResult
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.UserRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.File
import java.net.SocketTimeoutException

class CreateShipperViewModel(sessionManager: SessionManager): ViewModel() {
    private val userService = UserRepository(sessionManager).create(ApiService::class.java)
    var fullname: String = ""
    var phone: String = ""
    var license: String = ""
    var avatarPath: String = ""
        set(value) {
            field = value
            avatar = File(value)
        }
    private var avatar: File? = null
    var vehiclePath: String = ""
        set(value) {
            field = value
            vehicle = File(value)
        }
    private var vehicle: File? = null

    fun createShipper(onSuccess: () -> Unit) {
        val fullnamePart = fullname.toRequestBody("text/plain".toMediaTypeOrNull());
        val phonePart = phone.toRequestBody("text/plain".toMediaTypeOrNull());
        val licensePart = license.toRequestBody("text/plain".toMediaTypeOrNull());
        val reqAvatar = avatar!!.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val avatarPart = MultipartBody.Part.createFormData("avatar", avatar?.name!!, reqAvatar)
        val reqVehicle = vehicle!!.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val vehiclePart = MultipartBody.Part.createFormData("vehicle_image", vehicle?.name!!, reqVehicle)
        userService.createShipper(fullnamePart, phonePart, licensePart, avatarPart, vehiclePart).enqueue(object: retrofit2.Callback<ApiResult<Nothing>> {
            override fun onResponse(
                call: Call<ApiResult<Nothing>>,
                response: Response<ApiResult<Nothing>>
            ) {
                if (!response.isSuccessful) {
                    Log.e("FOODAPP:CreateShipperViewModel" ,response.errorBody().toString())
                } else {
                    onSuccess()
                }
            }
            override fun onFailure(call: Call<ApiResult<Nothing>>, t: Throwable) {
                if (t is SocketTimeoutException) {
                    onSuccess()
                } else {
                    Log.e("FOODAPP:CreateShipperViewModel", t.stackTraceToString())
                }
            }
        })
    }

    fun validate(): Boolean {
        return fullname.isNotBlank() && phone.isNotBlank() && license.isNotBlank() && avatar != null && vehicle != null
    }
}