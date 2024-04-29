package com.foodapp.view.Dialog_fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.view.adapter.KeyWordRecentAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Fragment_Likes :Fragment(R.layout.fragment_likes) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_likes, container, false)
        init(view)
        return view;
    }
    private fun init(view: View) {
    }

    override fun onStart() {
        super.onStart()

    }
}


