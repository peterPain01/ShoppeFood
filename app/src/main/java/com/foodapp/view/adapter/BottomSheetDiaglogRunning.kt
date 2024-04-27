package com.foodapp.view.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.data.model.OrderRunning
import com.foodapp.utils.FakeData
import com.foodapp.viewmodel.ShopViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDiaglogRunning : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_running, container, false)

        val dummyList = FakeData.createRunningOrder()
        val recyclerView_vertical = view.findViewById<RecyclerView>(R.id.dash_board_list_item)
        // Inflate the layout for this fragment
        val dummyData = FakeData.createRunningOrder()

        val adapter_vertical = runningOrderAdapter(dummyData, R.layout.item_grid_running)
        recyclerView_vertical.layoutManager = GridLayoutManager(context, 1)
        recyclerView_vertical.adapter = adapter_vertical
        return view
    }
}