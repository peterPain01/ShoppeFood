package com.foodapp.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.foodapp.R
import com.foodapp.databinding.FragmentShopInfoBinding
import com.foodapp.databinding.FragmentUserInfoBinding
import com.foodapp.viewmodel.ShopViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShopInfo.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShopInfo : Fragment() {
    private lateinit var binding: FragmentShopInfoBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentShopInfoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.viewModel = ShopViewModel(requireContext())
        return binding.root;
    }

    override fun onStart() {
        super.onStart()
        binding.viewModel?.getInfo(requireView().findViewById<ImageView>(R.id.activity_user_info_image))
    }
}