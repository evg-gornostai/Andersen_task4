package com.gornostai.customwatch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.util.*

class MyWatch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(
    context,
    attrs,
    defStyle
) {

    private val clockFacePaint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        strokeWidth = context.resources.getDimension(R.dimen.watch_strokeWidth)
        style = Paint.Style.STROKE
    }

    private val secondHandPaint = Paint().apply {
        color = Color.RED
        strokeWidth = context.resources.getDimension(R.dimen.watch_strokeWidth)
    }

    private val minuteHandPaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = context.resources.getDimension(R.dimen.watch_strokeWidth)
    }

    private val hourHandPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = context.resources.getDimension(R.dimen.watch_strokeWidth)
    }

    private var widthCenter = 0f
    private var heightCenter = 0f
    private val radius = 300f

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        widthCenter = width / 2f
        heightCenter = height / 2f
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val timeSeconds = System.currentTimeMillis()/1000

        //the clock face
        canvas.drawCircle(
            widthCenter,
            heightCenter,
            radius,
            clockFacePaint)

        for (i in 1..12) {
            canvas.save()
            canvas.rotate(
                (30 * i).toFloat(),
                widthCenter,
                heightCenter
            )
            canvas.drawLine(
                widthCenter,
                heightCenter + 300,
                widthCenter,
                heightCenter + 250,
                clockFacePaint
            )
            canvas.restore()
        }

        // the second hand
        canvas.save()
        canvas.rotate(
            (360f/60f * (timeSeconds%60)),
            widthCenter,
            heightCenter
        )
        canvas.drawLine(
            widthCenter,
            heightCenter + 80,
            widthCenter,
            heightCenter - 300,
            secondHandPaint
        )
        canvas.restore()

        //the minute hand
        canvas.save()
        canvas.rotate(
            (360f/3600f * (timeSeconds%3600)),
            widthCenter,
            heightCenter
        )
        canvas.drawLine(
            widthCenter,
            heightCenter + 50,
            widthCenter,
            heightCenter - 250,
            minuteHandPaint
        )
        canvas.restore()

        //the hour hand
        canvas.save()
        canvas.rotate(
            (360f/43200f * (timeSeconds%43200)),
            widthCenter,
            heightCenter
        )
        canvas.drawLine(
            widthCenter,
            heightCenter + 30,
            widthCenter,
            heightCenter - 200,
            hourHandPaint
        )
        canvas.restore()

        invalidate()
    }
}





















