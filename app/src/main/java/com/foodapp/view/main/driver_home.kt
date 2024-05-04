package com.foodapp.view.main

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.foodapp.R
import com.foodapp.data.model.MapPosition
import com.foodapp.data.model.UserAddress
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.ActivityDriverHomeBinding
import com.foodapp.helper.LocationHelper
import com.foodapp.helper.PathJSONParser
import com.foodapp.helper.helper
import com.foodapp.view.Dialog_fragment.FragmentDriverOnShipping
import com.foodapp.viewmodel.DriverHomeViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URL


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class driver_home : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityDriverHomeBinding
    private lateinit var locationHelper: LocationHelper
    private lateinit var mNewOrderReceiver: BroadcastReceiver
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var orderId: String? = null
    private var userAddress: UserAddress? = null
    private var shopAddress: UserAddress? = null
    private var price: Double = 0.0
    private lateinit var fragment: FragmentDriverOnShipping

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDriverHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        locationHelper = LocationHelper(this)
        binding.lifecycleOwner = this
        binding.viewModel = DriverHomeViewModel(SessionManager(this)) {
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
            binding.viewModel?.updateToken(token)
        })

        initOnNewOrderFunction()
    }

    private fun initOnNewOrderFunction() {
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data = result.data!!
            userAddress = data.getSerializableExtra("userAddress", UserAddress::class.java)
            shopAddress = data.getSerializableExtra("shopAddress", UserAddress::class.java)
            price = data.getDoubleExtra("price", 0.0)
            when (result.resultCode) {
                RESULT_OK -> binding.viewModel?.confirmOrder(orderId!!, {
                    Log.d("FOODAPP:driver_home", "successfully confirm order")
                    fragment = FragmentDriverOnShipping(userAddress, shopAddress, price) {
                        binding.viewModel?.finishOrder(orderId!!) {
                            removeFragment(fragment)
                            userAddress = null
                            shopAddress = null
                            price = 0.0
                            mMap.clear()
                        }
                    }
                    replaceFragment(fragment)
                    if (shopAddress != null && userAddress != null) {
                        lifecycleScope.launch(Dispatchers.IO) { drawPath(shopAddress?.latlng!!, userAddress?.latlng!!) }
                    } else {
                        Log.e("FOODAPP:driver_home", "Missing address user = ${userAddress.toString()}, shop = ${shopAddress.toString()}")
                    }
                }, {
                    Log.d("FOODAPP:driver_home", "fail to confirm order")
                })
                RESULT_CANCELED -> Log.d("FOODAPP:driver_home", "cancel")
            }
        }

        val thisContext: AppCompatActivity = this
        mNewOrderReceiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                helper.displayConfirmCancelPopup(thisContext, "You have a new order", {
                    orderId = intent?.getStringExtra("orderId")
                    Log.d("FOODAPP:driver_home", orderId.toString())
                    if (orderId == null) {
                        Log.e("FOODAPP:driver_home", "Missing orderId")
                        throw Exception("Missing orderId")
                    }
                    val new_intent = Intent(thisContext, driver_confirm::class.java)
                    new_intent.putExtra("orderId", orderId)
                    resultLauncher.launch(new_intent)
                }, {
                    Log.d("FOODAPP:driver_home", "cancel")
                })
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
                binding.viewModel?.updateLocation(MapPosition(lat = it.latitude, lng = it.longitude))
                val pos = LatLng(it.latitude, it.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(pos))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 17.0f))
            }
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.activity_driver_home_fragment_container, fragment)
        fragmentTransaction.commit()
    }
    private fun removeFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.remove(fragment)
        fragmentTransaction.commit()
    }
    private fun putPolylines(routes: List<List<LatLng>>) {
        var polyLineOptions: PolylineOptions? = null
        routes.forEach {
            polyLineOptions = PolylineOptions()
            polyLineOptions?.addAll(it)
            polyLineOptions?.width(2f)
            polyLineOptions?.color(Color.BLUE)
            mMap.addPolyline(polyLineOptions!!)
        }
    }

    private fun getMapsApiDirectionsUrl(start: MapPosition, end: MapPosition): String {
        val origin = "origin=${start.lat},${start.lng}"
        val destination = "destination=${end.lat},${end.lng}"
        val GG_API_KEY = this.packageManager.getApplicationInfo(this.packageName, PackageManager.GET_META_DATA)
            .metaData.getString("com.google.android.geo.API_KEY", "")
        val key="key=$GG_API_KEY"
        val params = "$origin&$destination&$key"
        val output = "json"
        return "https://maps.googleapis.com/maps/api/directions/$output?$params"
    }

    private suspend fun drawPath(start: MapPosition, end: MapPosition) {
        val url = URL(getMapsApiDirectionsUrl(start, end))
        val inputStream = url.content as InputStream
        val obj = JsonParser().parse(InputStreamReader(inputStream)).asJsonObject!!
        val ok = obj.get("status").asString == "OK"
        if (ok) {
            val (routes, distance, duration) = PathJSONParser().parse(obj)
            withContext(Dispatchers.Main) {
                fragment.updateDisDur(distance.toDouble()/1000.0, helper.formatDuration(duration.toLong()))
                putPolylines(routes)
                mMap.addMarker(MarkerOptions().position(LatLng(start.lat, start.lng)).title("Start"))
                mMap.addMarker(MarkerOptions().position(LatLng(end.lat, end.lng)).title("End"))
            }
        } else {
            Log.e("FOODAPP:driver_home", obj.toString())
        }
    }
}