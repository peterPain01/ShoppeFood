package com.foodapp.helper

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.foodapp.R
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executors


open class helper {
    enum class PopupType { Info, Error }
    companion object {
        @JvmStatic
        fun ShowImageUrl(url: String?, imageView: ImageView) {
            if (url == null || url == "") return
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
        fun drawableFromUrl(url: String?): BitmapDrawable? {
            if (url == null || url.trim() == "") return null
            val x = BitmapFactory.decodeStream(URL(url).content as InputStream)
            return BitmapDrawable(Resources.getSystem(), x)
        }

        fun displayPopup(context: AppCompatActivity, str: String, type: PopupType, onDismiss: () -> Unit) {
            val inflater: LayoutInflater = LayoutInflater.from(context)!!
            val view: View = inflater.inflate(R.layout.error_popup, context.window.decorView.findViewById<ViewGroup>(android.R.id.content), false)
            view.findViewById<TextView>(R.id.error_text).text = str
            val image = view.findViewById<ImageView>(R.id.error_popup_image)
            image.imageTintList = ColorStateList.valueOf(Color.parseColor(when (type) {
                PopupType.Error -> "#ff0000"
                PopupType.Info -> "#0000ff"
            }))
            image.setImageResource(when (type) {
                PopupType.Error -> android.R.drawable.stat_notify_error
                PopupType.Info -> android.R.drawable.ic_dialog_info
            })
            val width = LinearLayout.LayoutParams.MATCH_PARENT
            val height = LinearLayout.LayoutParams.MATCH_PARENT
            val focusable = true // lets taps outside the popup also dismiss it

            val popupWindow = PopupWindow(view, width, height, focusable)
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
            view.setOnClickListener {
                popupWindow.dismiss()
            }
            popupWindow.setOnDismissListener {
                onDismiss()
            }
        }
    }
}