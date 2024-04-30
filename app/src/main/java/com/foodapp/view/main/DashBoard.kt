package com.foodapp.view.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.utils.FakeData
import com.foodapp.view.adapter.BottomSheetDiaglogRunning
import com.foodapp.view.adapter.GridAdapter
import com.foodapp.viewmodel.ShopViewModel
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.bottomsheet.BottomSheetBehavior

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
        var shopViewModel = ShopViewModel(requireActivity());
        val lineChart: LineChart = view.findViewById(R.id.lineChart)
        val gridView = view.findViewById<RecyclerView>(R.id.dash_board_recycleview)
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
        drawLineChart(lineChart) // Pass the LineChart view to the function

        //call api
        shopViewModel.getStatistic(view, gridView);

        return view
    }

    fun drawLineChart(lineChart: LineChart) {
        val entries = arrayListOf<Entry>().apply {
            add(Entry(0f, 4f))
            add(Entry(1f, 8f))
            add(Entry(2f, 6f))
            add(Entry(3f, 2f))
            add(Entry(4f, 7f))
        }

        val dataSet = LineDataSet(entries, "Sample Data")

        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.RED

        val lineData = LineData(dataSet)

        lineChart.data = lineData

        lineChart.description.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true
        lineChart.setScaleEnabled(true)
        lineChart.setDrawGridBackground(false)
        lineChart.setBackgroundColor(Color.WHITE)

        val xAxis: XAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val leftAxis: YAxis = lineChart.axisLeft
        leftAxis.setDrawGridLines(true)

        val rightAxis: YAxis = lineChart.axisRight
        rightAxis.isEnabled = false

        val legend: Legend = lineChart.legend
        legend.form = Legend.LegendForm.LINE
    }


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
