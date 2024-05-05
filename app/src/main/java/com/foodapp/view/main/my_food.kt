package com.foodapp.view.main

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.utils.FakeData
import com.foodapp.view.adapter.myFoodAdapter
import com.foodapp.view.adapter.runningOrderAdapter
import androidx.fragment.app.FragmentManager
import com.foodapp.viewmodel.ShopViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [my_food.newInstance] factory method to
 * create an instance of this fragment.
 */
class my_food(val supportFragmentManager : FragmentManager) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_my_food, container, false)

        val all = view.findViewById<TextView>(R.id.my_food_all)
        val publish = view.findViewById<TextView>(R.id.my_food_publish)
        val unPublish = view.findViewById<TextView>(R.id.my_food_UnPublish)
        val recyclerView_vertical = view.findViewById<RecyclerView>(R.id.my_food_total_items)
        var viewModel = ShopViewModel(requireActivity());
        viewModel.getProduct(view, recyclerView_vertical, "all")

        all?.setTextColor(Color.parseColor("#FB6D3A"))
        all?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FB6D3A"))
        var prev: TextView = all;

        all.setOnClickListener {
            prev?.setTextColor(Color.parseColor("#A5A7B9"))
            prev?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#A5A7B9"))

            all?.setTextColor(Color.parseColor("#FB6D3A"))
            all?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FB6D3A"))
            prev = all;
            viewModel.getProduct(view, recyclerView_vertical, "all")
        }

        publish.setOnClickListener {
            prev?.setTextColor(Color.parseColor("#A5A7B9"))
            prev?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#A5A7B9"))
            publish?.setTextColor(Color.parseColor("#FB6D3A"))
            publish?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FB6D3A"))
            prev = publish
            viewModel.getProduct(view, recyclerView_vertical, "publish")
        }

        unPublish.setOnClickListener {
            if(prev != null) {
                prev?.setTextColor(Color.parseColor("#A5A7B9"))
                prev?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#A5A7B9"))
            }
            unPublish?.setTextColor(Color.parseColor("#FB6D3A"))
            unPublish?.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#FB6D3A"))
            prev = unPublish
            viewModel.getProduct(view, recyclerView_vertical, "unPublish")
        }

        return view;
    }
}
