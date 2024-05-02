package com.foodapp.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.data.model.User
import com.foodapp.data.model.UserAddress
import com.foodapp.data.model.auth.SessionManager
import com.foodapp.databinding.ActivityUserAddressBinding
import com.foodapp.view.adapter.AddressAdapter
import com.foodapp.viewmodel.UserAddressViewModel

class UserAddress : AppCompatActivity() {
    private lateinit var backBtn: View
    private lateinit var addBtn: View
    private lateinit var binding: ActivityUserAddressBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var user: User
    private lateinit var adapter: AddressAdapter
    companion object {
        val ADD_CODE: Int = 400
        val UPDATE_CODE: Int = 401
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_address)
        user = intent.getSerializableExtra("info", User::class.java)!!
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_address)
        binding.lifecycleOwner = this
        binding.viewModel = UserAddressViewModel(SessionManager(this)) {
            Toast.makeText(this, it ?: "", Toast.LENGTH_LONG).show()
        }
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val data: Intent? = result.data
            val address = data?.getSerializableExtra("address", UserAddress::class.java)
            address?.let {
                when (result.resultCode) {
                    ADD_CODE -> {
                        adapter.add(it)
                    }

                    UPDATE_CODE -> {
                        val index = data.getIntExtra("edittingIndex", -1) ?: -1
                        if (index != -1) {
                            adapter.updateAt(index, it)
                        }
                    }

                    else -> {
                        Log.d("FOODAPP:UserAddress", result.resultCode.toString())
                    }
                }
                binding.viewModel?.updateUser(user)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        adapter = AddressAdapter(user.addresses, R.layout.item_address, { addr, index ->
            val newIntent = Intent(this, ManageAddress::class.java)
            newIntent.putExtra("address", addr)
            newIntent.putExtra("edittingIndex", index)
            resultLauncher.launch(newIntent)
        }, {
            binding.viewModel?.updateUser(user)
        })
        binding.activityUserAddressAddrList.adapter = adapter
        binding.activityUserAddressBackBtn.setOnClickListener { this.finish() }
        binding.activityUserAddressAddBtn.setOnClickListener {
            val newIntent = Intent(this, ManageAddress::class.java)
            val temp: com.foodapp.data.model.UserAddress? = null
            newIntent.putExtra("address", temp);
            resultLauncher.launch(newIntent)
        }
    }
}