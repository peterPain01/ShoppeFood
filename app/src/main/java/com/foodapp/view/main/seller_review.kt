package com.foodapp.view.main

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.foodapp.R
import com.foodapp.utils.FakeData
import com.foodapp.view.adapter.NotifyAdapter
import com.foodapp.view.adapter.reviewAdapter
import com.foodapp.viewmodel.CommentViewModel

class seller_review : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seller_review)
        val recyclerView_vertical = findViewById<RecyclerView>(R.id.seller_review_list_notify)
        CommentViewModel(this, recyclerView_vertical);

        //val dummyList = FakeData.createReview()
        val BtnBack = findViewById<ImageButton>(R.id.seller_review_imageButton2)

        BtnBack.setOnClickListener {
            finish();
        }
    }
}