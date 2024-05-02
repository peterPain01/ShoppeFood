package com.foodapp.view.Dialog_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foodapp.R

class FragmentNotification: Fragment(R.layout.fragment_notification) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notification, container, false)
        init(view)
        return view;
    }

    private fun init(view : View)
    {

    }

    override fun onStart() {
        super.onStart()
    }



}