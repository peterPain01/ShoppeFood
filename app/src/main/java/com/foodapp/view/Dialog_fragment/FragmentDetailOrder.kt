package com.foodapp.view.Dialog_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.FragmentDetailOrderBinding
import com.foodapp.viewmodel.OrderDetailViewModel

class FragmentDetailOrder(val orderId: String) : Fragment(R.layout.fragment_detail_order) {
    private lateinit var binding: FragmentDetailOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailOrderBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = OrderDetailViewModel(SessionManager(this.requireContext()), orderId) {
            Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
            Log.d("FOODAPP:FragmentDetailOrder", it)
        }
        binding.viewModel?.order?.observe(viewLifecycleOwner) {
            it?.let {
                Glide.with(this)
                    .load(it.order_shop.image)
                    .into(binding.fragmentDetailOrderImage)
            }
        }
        return binding.root
    }
}