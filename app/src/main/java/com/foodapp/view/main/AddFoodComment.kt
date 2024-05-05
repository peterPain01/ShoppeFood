package com.foodapp.view.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.foodapp.R
import com.foodapp.data.model.Review
import com.foodapp.databinding.ActivityAddFoodCommentBinding
import com.foodapp.helper.helper

class AddFoodComment : AppCompatActivity() {
    private lateinit var binding: ActivityAddFoodCommentBinding
    private lateinit var starList: List<ImageView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food_comment)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_food_comment)
        binding.lifecycleOwner = this
        binding.review = Review()
        starList = listOf(
            binding.activityAddFoodStart1,
            binding.activityAddFoodStart2,
            binding.activityAddFoodStart3,
            binding.activityAddFoodStart4,
            binding.activityAddFoodStart5,
            )
    }

    override fun onStart() {
        super.onStart()
        binding.activityAddFoodCommentBack.setOnClickListener { this.finish() }
        for (i in 0..<starList.count()) {
            starList[i].setOnClickListener {
                for (j in 0..i) {
                    starList[j].setImageResource(R.drawable.star)
                }
                for (j in i+1..<starList.count()) {
                    starList[j].setImageResource(R.drawable.ic_star)
                }
                binding.review!!.comment_star = (i+1)
            }
        }
        binding.activityAddFoodCommentBtn.setOnClickListener {
            if (validate()) {
                val data = Intent()
                data.putExtra("review", binding.review)
                setResult(RESULT_OK, data)
                finish()
            } else {
                helper.displayPopup(this, "Please fill all missing fields!", helper.PopupType.Error) {}
            }
        }
    }

    fun validate(): Boolean {
        val rv = binding.review!!
        return rv.comment_star != 0 && rv.comment_title.isNotBlank() && rv.comment_content_text.isNotBlank()
    }
}