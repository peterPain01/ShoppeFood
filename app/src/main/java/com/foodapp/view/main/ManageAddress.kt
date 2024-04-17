package com.foodapp.view.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.foodapp.R
import com.foodapp.databinding.ActivityManageAddressBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL

class ManageAddress : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityManageAddressBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityManageAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            mMap.isMyLocationEnabled = true
        }
        // Add a marker in Sydney and move the camera
        val hcmus = LatLng(10.762849599401113, 106.68251290899643)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hcmus))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 12.0f))
        val marker = mMap.addMarker(MarkerOptions().position(mMap.cameraPosition.target).title("Position"))
        mMap.setOnCameraMoveListener {
            marker?.position = mMap.cameraPosition.target
        }
        mMap.setOnCameraIdleListener { // call when stop moving (once)
            try {
                updatePosition(marker?.position!!)
            } catch (ex: Exception) {
                Log.d("TUONGERR1", ex.toString())
            }
        }
    }
    private fun updatePosition(pos: LatLng) {
        val thread = Thread(Runnable {
            try {
                val url =
                    URL("https://maps.googleapis.com/maps/api/geocode/json?result_type=street_address&latlng=${pos.latitude},${pos.longitude}&key=AIzaSyC0DeNxN37anGzdfW7uGQiwAueSlvnb8_U")
                val inputStream = url.content as InputStream
                val obj = JsonParser().parse(InputStreamReader(inputStream)).asJsonObject
                val address =
                    obj.get("results").asJsonArray.get(0).asJsonObject.get("formatted_address").asString
                with(Dispatchers.Main) {
                    Log.d("TUONG", address)
                    binding.activityManageAddressBox.setText(address)
                }
            } catch(ex: Exception) {
                Log.d("TUONGERROR", ex.toString())
            }
        })
        thread.start()
    }
}
