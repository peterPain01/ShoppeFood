package com.foodapp.utils

import com.foodapp.R
import com.foodapp.data.model.Photo

object getLocalData {
    fun getListBannerImage() : List<Photo>
    {
        val photoList = mutableListOf<Photo>();

        val resourceIdList = listOf(
            R.drawable.homepage_banner_1,
            R.drawable.homepage_banner_2,
            R.drawable.homepage_banner_3,
            R.drawable.homepage_banner_4,
            R.drawable.homepage_banner_5,
        )
        for (resourceId in resourceIdList) {
            val photo = Photo(resourceId)
            photoList.add(photo)
        }

        return photoList
    }
}