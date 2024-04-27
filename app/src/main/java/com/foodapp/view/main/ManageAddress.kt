package com.foodapp.view.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.foodapp.R
import com.foodapp.data.model.UserAddress
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.ActivityManageAddressBinding
import com.foodapp.viewmodel.ManageAddressViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class ManageAddress : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityManageAddressBinding
    private var address: UserAddress? = null
    private var edittingIndex: Int = -1
    private var isAdding: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val GG_API_KEY = this.packageManager.getApplicationInfo(this.packageName, PackageManager.GET_META_DATA)
            .metaData.getString("com.google.android.geo.API_KEY", "")

        address = intent.getSerializableExtra("address", UserAddress::class.java)
        edittingIndex = intent.getIntExtra("edittingIndex", -1)
        isAdding = address == null

        binding = ActivityManageAddressBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = ManageAddressViewModel(address, GG_API_KEY)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onStart() {
        super.onStart()
        binding.activityManageAddressSaveBtn.setOnClickListener {
            binding.viewModel?.updateAddress {
                val data = Intent()
                data.putExtra("address", it)
                if (!isAdding) data.putExtra("edittingIndex", edittingIndex)
                setResult(
                    if (isAdding) com.foodapp.view.main.UserAddress.ADD_CODE else com.foodapp.view.main.UserAddress.UPDATE_CODE,
                    data
                )
                finish()
            }
        }
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
        var initLatLng = LatLng(10.762849599401113, 106.68251290899643)
        address?.let {
            initLatLng = LatLng(it.latlng.lat, it.latlng.lng)
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(initLatLng))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(initLatLng, 17.0f))
        val marker = mMap.addMarker(MarkerOptions().position(mMap.cameraPosition.target).title("Position"))
        mMap.setOnCameraMoveListener {
            marker?.position = mMap.cameraPosition.target
        }
        mMap.setOnCameraIdleListener { // call when stop moving (once)
            binding.viewModel?.updateAddress(marker?.position!!)
        }
        binding.activityManageAddressBox.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                Log.d("FOODAPP:AddressChanged", binding.activityManageAddressBox.text.toString())
                binding.viewModel?.updateMap(binding.activityManageAddressBox.text.toString()) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
                }
            }
        }
    }
}
