package com.foodapp.view.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.view.adapter.BottomSheetDiaglogRunning
import com.foodapp.viewmodel.ShopViewModel
import com.github.mikephil.charting.charts.LineChart
import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.widget.ArrayAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashBoard.newInstance] factory method to
 * create an instance of this fragment.
 */
class DashBoard : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var shopViewModel: ShopViewModel
    private lateinit var mNewOrderReceiver: BroadcastReceiver
    private lateinit var lineChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dash_board, container, false)
        val showBottomSheetButton = view.findViewById<ImageButton>(R.id.dash_board_running)
        var spinner = view.findViewById<Spinner>(R.id.dash_board_filter)
        val arraySpinner = listOf(
            "Month", "Year"
        )

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, arraySpinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)

        shopViewModel = ShopViewModel(requireActivity());
        lineChart = view.findViewById(R.id.lineChart)
        val allreview = view.findViewById<TextView>(R.id.dash_board_view_all)

        allreview.setOnClickListener {
            val intent = Intent(requireActivity(), seller_review::class.java)
            requireActivity().startActivity(intent);
        }

        showBottomSheetButton.setOnClickListener {
            val bottomSheet = BottomSheetDiaglogRunning()
            bottomSheet.show(requireActivity().supportFragmentManager, "ModalBottomSheet")
        }
        // Get reference to LineChart view
//        drawLineChart(lineChart) // Pass the LineChart view to the function

        //call api
        // shopViewModel.getStatistic(view, gridView);
        return view
    }

    override fun onStart() {
        super.onStart()
        setUpNewOrderListener()
    }

    override fun onResume() {
        super.onResume()
        val gridView = this.requireView().findViewById<RecyclerView>(R.id.dash_board_recycleview)
        shopViewModel.getStatistic(this.requireView(), gridView, lineChart);
    }


    fun setUpNewOrderListener() {
        val view = this.requireView()
        mNewOrderReceiver = object: BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val textView = view.findViewById<TextView>(R.id.dash_board_location5)
                val cur = textView.text.toString()
                textView.text = (cur.toInt() + 1).toString()
            }
        }
        LocalBroadcastManager.getInstance(this.requireContext()).registerReceiver(mNewOrderReceiver, IntentFilter("new-order"))

    }

    // gui mang 1 mang cac diem cua thang trong ngay
    // select theo thang



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashBoard.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashBoard().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
