package com.foodapp.view.Dialog_fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.FragmentUserInfoBinding
import com.foodapp.view.CreateShipper
import com.foodapp.view.auth.Login
import com.foodapp.view.main.PersonalInfo
import com.foodapp.view.main.UserAddress
import com.foodapp.view.main.create_restaurant
import com.foodapp.viewmodel.UserInfoViewModel

class UserInfoFragment : Fragment(R.layout.fragment_user_info) {
    private lateinit var binding: FragmentUserInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)
//         = DataBindingUtil.setContentView(view, R.layout.activity_user_info)
        init()
        return binding.root;
    }

    private fun init() {
        binding.lifecycleOwner = this
        binding.viewModel = UserInfoViewModel(SessionManager(requireContext())){
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        binding.viewModel?.user?.observe(viewLifecycleOwner) {
            Glide.with(this)
                .load(it?.avatar)
                .into(binding.activityUserInfoImage)
        }
    }
    override fun onStart() {
        super.onStart()
        binding.activityUserInfoAddressContainer.setOnClickListener {
            val new_intent = Intent(requireContext(), UserAddress::class.java);
            new_intent.putExtra("info", binding.viewModel?.user?.value)
            startActivity(new_intent);
        }
        binding.activityUserInfoPersonalInfo.setOnClickListener {
            val new_intent = Intent(requireContext(), PersonalInfo::class.java);
            new_intent.putExtra("info", binding.viewModel?.user?.value)
            startActivity(new_intent);
        }
        binding.activityUserInfoLogout.setOnClickListener {
            binding.viewModel!!.logout {
                SessionManager(requireContext()).removeAuthToken()
                val new_intent = Intent(requireContext(), Login::class.java)
                startActivity(new_intent)
            }
        }
        binding.activityUserInfoCreateShop.setOnClickListener {
            val newIntent = Intent(requireContext(), create_restaurant::class.java)
            startActivity(newIntent)
        }
        binding.activityUserInfoCreateShipper.setOnClickListener {
            val newIntent = Intent(requireContext(), CreateShipper::class.java)
            startActivity(newIntent)
        }
    }
}