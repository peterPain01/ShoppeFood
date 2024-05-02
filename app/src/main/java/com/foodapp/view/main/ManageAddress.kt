package com.foodapp.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.foodapp.R
import com.foodapp.data.model.UserAddress
import com.foodapp.databinding.ActivityManageAddressBinding
import com.foodapp.helper.LocationHelper
import com.foodapp.viewmodel.ManageAddressViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


class ManageAddress : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityManageAddressBinding
    private var address: UserAddress? = null
    private var edittingIndex: Int = -1
    private var isAdding: Boolean = false
    private lateinit var currentActiveType: Button
    private val activeBgType = ColorStateList.valueOf(Color.parseColor("#F58D1D"))
    private val activeFgType = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
    private val inactiveBgType = ColorStateList.valueOf(Color.parseColor("#F0F5FA"))
    private val inactiveFgType = ColorStateList.valueOf(Color.parseColor("#32343E"))
    private lateinit var locationHelper: LocationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val GG_API_KEY = this.packageManager.getApplicationInfo(this.packageName, PackageManager.GET_META_DATA)
            .metaData.getString("com.google.android.geo.API_KEY", "")
        locationHelper = LocationHelper(this)
        address = intent.getSerializableExtra("address", UserAddress::class.java)
        edittingIndex = intent.getIntExtra("edittingIndex", -1)
        isAdding = address == null

        binding = ActivityManageAddressBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = ManageAddressViewModel(address, GG_API_KEY)
        val type = (address?.type ?: "home").lowercase()
        currentActiveType = if (type == "home") binding.activityManageAddressHomeBtn else if (type == "company") binding.activityManageAddressCompanyBtn else binding.activityManageAddressOtherBtn
        setActive(currentActiveType)
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
    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = locationHelper.isLocationAllowed
        if (locationHelper.isLocationAllowed) {
            mMap.setOnMyLocationButtonClickListener {
                locationHelper.getCurrentLocation {
                    it?.let {
                        Log.d("FOODAPP:ManageAddress", "CurLat: ${it.latitude}")
                        Log.d("FOODAPP:ManageAddress", "CurLng: ${it.longitude}")
                        binding.viewModel?.updateAddress(LatLng(it.latitude, it.longitude))
                    }
                }
                false
            }
        };
        var initLatLng: LatLng? = null
        var marker: Marker? = null
        initLatLng = if (address == null && locationHelper.isLocationAllowed) {
            locationHelper.getCurrentLocation {
                if (it == null)
                    Toast.makeText(this, "Cannot get location.", Toast.LENGTH_SHORT).show()
                else {
                    val pos = LatLng(it.latitude, it.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(pos))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 17.0f))
                    marker = mMap.addMarker(MarkerOptions().position(mMap.cameraPosition.target).title("Position"))
                }
            }
            null
        } else if (address != null) {
            LatLng(address?.latlng?.lat!!, address?.latlng?.lng!!)
        } else {
            LatLng(10.762849599401113, 106.68251290899643) // hcmus
        }
        initLatLng?.let {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it, 17.0f))
            marker = mMap.addMarker(MarkerOptions().position(mMap.cameraPosition.target).title("Position"))
        }
        mMap.setOnCameraMoveListener {
            marker?.let {
                it.position = mMap.cameraPosition.target
            }
        }
        mMap.setOnCameraIdleListener { // call when stop moving (once)
            marker?.let {
                binding.viewModel?.updateAddress(it.position)
            }
        }
        binding.activityManageAddressBox.setOnKeyListener { view, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                Log.d("FOODAPP:AddressChanged", binding.activityManageAddressBox.text.toString())
                binding.viewModel?.updateMap(binding.activityManageAddressBox.text.toString()) {
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(it))
                }
                (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
                view.clearFocus()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    fun onClickTypeChoose(view: View) {
        val btn = view as Button
        binding.viewModel?.setType(btn.text.toString())
        setInactive(currentActiveType)
        setActive(btn)
        currentActiveType = btn
    }
    fun setActive(view: Button) {
        view.backgroundTintList = activeBgType
        view.setTextColor(activeFgType)
    }
    fun setInactive(view: Button) {
        view.backgroundTintList = inactiveBgType
        view.setTextColor(inactiveFgType)
    }
}
