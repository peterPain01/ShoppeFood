package com.foodapp.view.Dialog_fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.foodapp.R
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.ActivityUserInfoBinding
import com.foodapp.databinding.FragmentUserInfoBinding
import com.foodapp.view.auth.Login
import com.foodapp.view.main.PersonalInfo
import com.foodapp.view.main.UserAddress
import com.foodapp.viewmodel.UserInfoViewModel

class UserInfoFragment : Fragment(R.layout.fragment_user_info) {
    private lateinit var binding: FragmentUserInfoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)
//         = DataBindingUtil.setContentView(view, R.layout.activity_user_info)
        init()
        return binding.root;
    }

    private fun init() {
        binding.lifecycleOwner = this
        binding.viewModel = UserInfoViewModel({
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }, {
            Glide.with(this)
                .load(it)
                .into(binding.activityUserInfoImage)
        }, SessionManager(requireContext()))
    }
    override fun onStart() {
        super.onStart()
        super.onStart()
        binding.activityUserInfoAddressContainer.setOnClickListener {
            val new_intent = Intent(context, UserAddress::class.java);
            new_intent.putExtra("info", binding.viewModel?.user?.value)
            startActivity(new_intent);
        }
        binding.activityUserInfoPersonalInfo.setOnClickListener {
            val new_intent = Intent(context, PersonalInfo::class.java);
            new_intent.putExtra("info", binding.viewModel?.user?.value)
            startActivity(new_intent);
        }
        binding.activityUserInfoLogout.setOnClickListener {
            binding.viewModel!!.logout {
                SessionManager(requireContext()).removeAuthToken()
                val new_intent = Intent(context, Login::class.java)
                startActivity(new_intent)
            }
        }
    }
}