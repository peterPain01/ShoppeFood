package com.foodapp.view.Dialog_fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.FragmentMyOrderBinding
import com.foodapp.viewmodel.MyOrderViewModel


class FragmentMyOrder :Fragment(R.layout.fragment_my_order) {
    private val ACTIVE_COLOR = (0xFFFF6622).toInt()
    private val INACTIVE_COLOR = (0xFFA5A7B9).toInt()
    private lateinit var binding: FragmentMyOrderBinding

    private enum class Tab { Ongoing, History }
    private var current_tab: Tab = Tab.History // placeholder, init with setter later
        set(value) {
            if (field != value) {
                field = value
                if (value == Tab.Ongoing) {
                    binding.activityMyOrderOngoingBtn.setTextColor(ACTIVE_COLOR)
                    binding.activityMyOrderOngoingBtn.backgroundTintList = ColorStateList.valueOf(ACTIVE_COLOR)
                    binding.activityMyOrderHistoryBtn.setTextColor(INACTIVE_COLOR)
                    binding.activityMyOrderHistoryBtn.backgroundTintList = ColorStateList.valueOf(INACTIVE_COLOR)
                    binding.viewModel?.loadOngoing()
                } else if (value == Tab.History) {
                    binding.activityMyOrderHistoryBtn.setTextColor(ACTIVE_COLOR)
                    binding.activityMyOrderHistoryBtn.backgroundTintList = ColorStateList.valueOf(ACTIVE_COLOR)
                    binding.activityMyOrderOngoingBtn.setTextColor(INACTIVE_COLOR)
                    binding.activityMyOrderOngoingBtn.backgroundTintList = ColorStateList.valueOf(INACTIVE_COLOR)
                    binding.viewModel?.loadHistory()
                } else {
                    throw Exception("Unknown type: " + value.name)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyOrderBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = MyOrderViewModel(SessionManager(requireContext())) {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            Log.d("FOODAPP:FragmentMyOrder", it)
        }
        return binding.root;
    }

    override fun onStart() {
        super.onStart()
        binding.activityMyOrderOngoingBtn.setOnClickListener { current_tab = Tab.Ongoing }
        binding.activityMyOrderHistoryBtn.setOnClickListener { current_tab = Tab.History }
        current_tab = Tab.Ongoing
    }
}