package com.foodapp.view.Dialog_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.foodapp.R
import com.foodapp.databinding.FragmentDriverOnShippingBinding
import com.foodapp.data.model.UserAddress

class FragmentDriverOnShipping(val userAddress: UserAddress?, val shopAddress: UserAddress?,
                               val price: Double,
                               val onFinishButton: (View) -> Unit) : Fragment(R.layout.fragment_driver_on_shipping) {
    private lateinit var binding: FragmentDriverOnShippingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentDriverOnShippingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.price = price
        binding.street = userAddress?.street ?: ""
        binding.fragmentDriverOnShippingFinishBtn.setOnClickListener(onFinishButton)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
    }
    fun updateDisDur(distance: Double, duration: String) {
        binding.fragmentDriverOnShippingDistance.text = String.format("%.2f Km", distance)
        binding.fragmentDriverOnShippingDuration.text = duration
    }
}