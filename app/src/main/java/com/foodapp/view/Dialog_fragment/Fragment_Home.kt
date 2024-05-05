package com.foodapp.view.Dialog_fragment

import ApiService
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.data.repository.RetrofitClient
import com.foodapp.databinding.FragmentHomeBinding
import com.foodapp.utils.getLocalData
import com.foodapp.view.adapter.PhotoViewPager2Adapter
import com.foodapp.view.main.AllShop
import com.foodapp.view.main.Order
import com.foodapp.view.main.Search
import com.foodapp.viewmodel.HomepageViewModel
import me.relex.circleindicator.CircleIndicator3

class Fragment_Home : Fragment(R.layout.fragment_home) {
    val service = RetrofitClient.retrofit.create(ApiService::class.java)

    lateinit var binding: FragmentHomeBinding
    val handler: Handler = Handler()
    val photoList = getLocalData.getListBannerImage();

    val runnable = object : Runnable {
        override fun run() {
            if (binding.homepageViewPager.currentItem == photoList.count() - 1)
                binding.homepageViewPager.setCurrentItem(0)
            else
                binding.homepageViewPager.setCurrentItem(binding.homepageViewPager.currentItem + 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        init()
        return binding.root;
    }

    private fun init() {
        binding.lifecycleOwner = this
        binding.viewModel = HomepageViewModel(SessionManager(requireContext())) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            Log.d("FOODAPP:Fragment_Home", it)
        }
        binding.homepageViewPager.adapter = PhotoViewPager2Adapter(photoList)
        intentActivity()
    }

    override fun onStart() {
        super.onStart()
        binding.homepageCircleIndicator.setViewPager(binding.homepageViewPager)
        binding.homepageViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 3000)
            }
        })
        binding.homepageSeeAll.setOnClickListener {
            val newIntent = Intent(this.requireContext(), AllShop::class.java)
            startActivity(newIntent)
        }
        binding.homepageRecyclerViewHorizontal.layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.HORIZONTAL, false)
    }

    override fun onResume() {
        super.onResume()
        binding.viewModel?.loadOverview()
    }

    fun intentActivity() {
        binding.homepageSearchBar.setOnClickListener {
            val intent = Intent(requireContext(), Search::class.java)
            startActivity(intent);
        }
        binding.homepageNotify.setOnClickListener {
            val intent = Intent(requireContext(), Order::class.java)
            startActivity(intent);
        }
    }
}