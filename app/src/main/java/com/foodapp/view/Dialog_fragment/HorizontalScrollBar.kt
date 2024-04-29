package com.foodapp.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class HorizontalScrollBar(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var scrollPosition = 0f
    private var maxScroll = 100f
    private val scrollPaint = Paint().apply {
        color = resources.getColor(android.R.color.holo_blue_light)
        strokeWidth = 10f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas?.drawLine(0f, height / 2.toFloat(), width * (scrollPosition / maxScroll), height / 2.toFloat(), scrollPaint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    val newScrollPosition = it.x.coerceIn(0f, width.toFloat())
                    if (newScrollPosition != scrollPosition) {
                        scrollPosition = newScrollPosition
                        invalidate()
                    }
                    return true
                }

                else -> {}
            }
        }
        return super.onTouchEvent(event)
    }
}
