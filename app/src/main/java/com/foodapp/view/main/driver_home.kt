package com.foodapp.view.main

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.foodapp.R
import com.foodapp.data.model.MapPosition
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.ActivityDriverHomeBinding
import com.foodapp.helper.LocationHelper
import com.foodapp.helper.helper
import com.foodapp.viewmodel.DriverHomeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class driver_home : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDriverHomeBinding
    private lateinit var locationHelper: LocationHelper
    private lateinit var viewModel: DriverHomeViewModel
    private lateinit var mNewOrderReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDriverHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locationHelper = LocationHelper(this)
        viewModel = DriverHomeViewModel(SessionManager(this)) {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            Log.d("FOODAPP:driver_home", it)
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.driver_home_driver_map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        var showMore = findViewById<ImageView>(R.id.driver_home_more);
        showMore.setOnClickListener {
            val new_intent = Intent(this, driver_info::class.java)
            startActivity(new_intent)
        }


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FOODAPP:driver_home", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            Log.d("FOODAPP:driver_home", token)
            viewModel.updateToken(token)
        })

        val thisContext: AppCompatActivity = this
        mNewOrderReceiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                helper.displayPopup(thisContext, "You have a new order", helper.PopupType.Info) {
                    Log.d("FOODAPP:driver_home", "OK")
                }
            }
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(mNewOrderReceiver, IntentFilter("new-order"))
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mNewOrderReceiver)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = locationHelper.isLocationAllowed
        locationHelper.getCurrentLocation {
            if (it == null)
                Toast.makeText(this, "Cannot get location.", Toast.LENGTH_SHORT).show()
            else {
                viewModel.updateLocation(MapPosition(lat = it.latitude, lng = it.longitude))
                val pos = LatLng(it.latitude, it.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(pos))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 17.0f))
            }
        }
    }
}