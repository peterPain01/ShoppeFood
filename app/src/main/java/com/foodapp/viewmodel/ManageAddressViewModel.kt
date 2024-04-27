package com.foodapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foodapp.data.model.MapPosition
import com.foodapp.data.model.UserAddress
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.timerTask


class ManageAddressViewModel(data: UserAddress?, val GG_API_KEY: String): ViewModel() {
    var address: MutableLiveData<UserAddress> = MutableLiveData(data ?: UserAddress())
    private val TIMEOUT: Long = 2000
    private var updateAddrTask: TimerTask? = null // handle rate limit TIMEOUT after stop moving camera
    val activeBgType = "#F58D1D"
    val inactiveBgType = "#F0F5FA"
    val activeFgType = "#FFFFFF"
    val inactiveFgType = "#32343E"

    fun updateAddress(pos: LatLng) {
        updateAddrTask?.cancel()
        updateAddrTask = timerTask {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val url =
                        URL("https://maps.googleapis.com/maps/api/geocode/json?result_type=street_address&latlng=${pos.latitude},${pos.longitude}&key=${GG_API_KEY}")
                    val inputStream = url.content as InputStream
                    val obj = JsonParser().parse(InputStreamReader(inputStream)).asJsonObject
                    val ok = obj.get("status").asString == "OK"
                    if (ok) {
                        val addr = obj.get("results").asJsonArray.get(0).asJsonObject.get("formatted_address").asString
                        Log.d("FOODAPP:UpdateAddress", addr)
                        val newUserAddress = address.value!!.let {
                            UserAddress(
                                name = it.name,
                                type = it.type,
                                street = addr,
                                latlng = MapPosition(lat = pos.latitude, lng = pos.longitude)
                            )
                        }
                        withContext (Dispatchers.Main) {
                            address.value = newUserAddress
                        }
                    } else {
                        Log.e("FOODAPP:UpdateAddress", obj.toString())
                    }
                } catch(ex: Exception) {
                    Log.e("FOODAPP:UpdateAddress-ERROR", ex.toString())
                }
            }
        }
        Timer().schedule(updateAddrTask, TIMEOUT)
    }
    fun updateMap(addr: String, moveMap: (LatLng) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val urlAddress = java.net.URLEncoder.encode(addr, "utf-8")
                val url = URL("https://maps.googleapis.com/maps/api/geocode/json?address=${urlAddress}&key=${GG_API_KEY}")
                val inputStream = url.content as InputStream
                val obj = JsonParser().parse(InputStreamReader(inputStream)).asJsonObject
                val ok = obj.get("status").asString == "OK"
                if (ok) {
                    val location =
                        obj.get("results").asJsonArray[0].asJsonObject.get("geometry").asJsonObject.get("location").asJsonObject
                    val lat = location.get("lat").asDouble
                    val lng = location.get("lng").asDouble
                    withContext (Dispatchers.Main) {
                        moveMap(LatLng(lat, lng))
                        val newUserAddress = address.value!!.let {
                            UserAddress(
                                name = it.name,
                                type = it.type,
                                street = addr,
                                latlng = MapPosition(lat = lat, lng = lng)
                            )
                        }
                        address.value = newUserAddress
                    }
                } else {
                    Log.e("FOODAPP:UpdateAddress", obj.toString())
                }
            } catch(ex: Exception) {
                Log.e("FOODAPP:UpdateMap-ERROR", ex.toString())
            }
        }
    }
    fun updateAddress(done: (UserAddress) -> Unit) {
        address.value?.let(done)
    }
    fun setType(type: String) {
        address.value?.type = type
    }
}