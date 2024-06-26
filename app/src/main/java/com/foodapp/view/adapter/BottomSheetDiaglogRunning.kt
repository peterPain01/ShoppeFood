package com.foodapp.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.OrderRunning
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.utils.FakeData
import com.foodapp.viewmodel.RunningOrderViewModel
import com.foodapp.viewmodel.ShopViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDiaglogRunning : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_running, container, false)

        val recyclerView_vertical = view.findViewById<RecyclerView>(R.id.dash_board_list_item)
        RunningOrderViewModel(requireContext(), recyclerView_vertical, SessionManager(requireContext()));

        return view
    }
}