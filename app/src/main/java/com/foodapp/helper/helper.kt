package com.foodapp.helper

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Handler
import android.os.Looper
import android.text.format.DateUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.foodapp.R
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.text.DecimalFormat
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

        private fun createPopup(context: AppCompatActivity, res: Int, bind: (View, PopupWindow) -> Unit, width: Int = LayoutParams.MATCH_PARENT, height: Int = LayoutParams.MATCH_PARENT) {
            val inflater: LayoutInflater = LayoutInflater.from(context)!!
            val view: View = inflater.inflate(res, context.window.decorView.findViewById<ViewGroup>(android.R.id.content), false)
            val focusable = true // lets taps outside the popup also dismiss it
            val popupWindow = PopupWindow(view, width, height, focusable)
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
            bind(view, popupWindow)
        }

        fun displayPopup(context: AppCompatActivity, str: String, type: PopupType, onDismiss: () -> Unit) {
            createPopup(context, R.layout.simple_popup, { view, popupWindow ->
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
                popupWindow.setOnDismissListener(onDismiss)
                view.setOnClickListener{ popupWindow.dismiss() }
            })
        }

        fun displayConfirmCancelPopup(context: AppCompatActivity, str: String, onConfirm: () -> Unit, onCancel: () -> Unit) {
            createPopup(context, R.layout.confirm_order_popup, {view, popupWindow ->
                val textView = view.findViewById<TextView>(R.id.confirm_order_popup_text)
                val confirmBtn = view.findViewById<Button>(R.id.confirm_order_popup_confirm_btn)
                val cancelBtn = view.findViewById<Button>(R.id.confirm_order_popup_cancel_btn)
                textView.text = str
                confirmBtn.setOnClickListener { onConfirm(); popupWindow.dismiss() }
                cancelBtn.setOnClickListener { onCancel(); popupWindow.dismiss() }
            })
        }
        fun formatter(n: Int) =
            DecimalFormat("#,###")
                .format(n)
                .replace(",", ".")
        fun formatDuration(seconds: Long): String = if (seconds < 60) {
            seconds.toString()
        } else {
            DateUtils.formatElapsedTime(seconds)
        }
    }
}