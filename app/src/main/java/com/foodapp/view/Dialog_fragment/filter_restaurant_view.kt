package com.foodapp.view.Dialog_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.foodapp.R

class filter_restaurant_view : DialogFragment() {
    private lateinit var linearLayout: LinearLayout
    private lateinit var closefragment: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_filter_restaurant_view, container, false)
        init(view)
        return view;
    }
    fun init(view: View) {
        linearLayout = view.findViewById(R.id.fragment_filter_restaurant_view_LinearLayout)
        closefragment = view.findViewById(R.id.fragment_filter_restaurant_view_close)
    }
    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(resources.getDimensionPixelSize(R.dimen.dialog_width),
            resources.getDimensionPixelSize(R.dimen.dialog_height))
        addButtons()
        closeDialog()
    }

    fun addButtons() {
        val linearLayout = view?.findViewById<LinearLayout>(R.id.fragment_filter_restaurant_view_LinearLayout)

        val buttonCount = 5

        for (i in 1..buttonCount) {
            val button = Button(requireContext())
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                dpToPx(46)
            ).apply {
                marginStart = dpToPx(20)
            }
            button.background = ContextCompat.getDrawable(requireContext(), R.drawable.detail_page_btn_radius)
            button.text = "Button $i"
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            button.setOnClickListener {
                // Button click action
            }
            linearLayout?.addView(button)
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
    fun closeDialog() {
        closefragment.setOnClickListener {
            dismiss()
        }
    }
}