package com.foodapp.helper

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors


class helper {
    companion object {
        @JvmStatic
        fun ShowImageUrl(url: String, imageView: ImageView) {
            val executor = Executors.newSingleThreadExecutor()
            val handler = Handler(Looper.getMainLooper())
            var image: Bitmap? = null
            executor.execute {
                val imageURL = url
                try {
                    val `in` = java.net.URL(imageURL).openStream()
                    image = BitmapFactory.decodeStream(`in`)
                    handler.post {
                        imageView?.setImageBitmap(image)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        @Throws(IOException::class)
        fun drawableFromUrl(url: String?): Drawable? {
            if (url == null || url.trim() == "") return null
            val x: Bitmap
            val input = URL(url).content as InputStream
            x = BitmapFactory.decodeStream(input)
            return BitmapDrawable(Resources.getSystem(), x)
        }
    }
}