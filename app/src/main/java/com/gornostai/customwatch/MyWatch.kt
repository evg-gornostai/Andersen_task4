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

    init {
        init()
        initializeAttributes(attrs, defStyle)
    }

    private val clockFacePaint = Paint().apply {
        color = Color.BLACK
        isAntiAlias = true
        strokeWidth = context.resources.getDimension(R.dimen.watch_strokeWidth)
        style = Paint.Style.STROKE
    }

    private lateinit var secondHandPaint: Paint
    private lateinit var minuteHandPaint: Paint
    private lateinit var hourHandPaint: Paint

    private var secondHandSize = 0f
    private var minuteHandSize = 0f
    private var hourHandSize = 0f

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
            heightCenter - secondHandSize,
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
            heightCenter - minuteHandSize,
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
            heightCenter - hourHandSize,
            hourHandPaint
        )
        canvas.restore()

        invalidate()
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyle: Int){
        if (attrs == null) return
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyWatch, defStyle,0)

        val colorSecondHand = typedArray.getColor(R.styleable.MyWatch_colorSecondHand, Color.RED)
        secondHandPaint.color = colorSecondHand
        val colorMinuteHand = typedArray.getColor(R.styleable.MyWatch_colorMinuteHand, Color.BLUE)
        minuteHandPaint.color = colorMinuteHand
        val colorHourHand = typedArray.getColor(R.styleable.MyWatch_colorHourHand, Color.BLACK)
        hourHandPaint.color = colorHourHand
        val sizeSecondHand = typedArray.getDimension(R.styleable.MyWatch_sizeSecondHand,300f)
        secondHandSize = sizeSecondHand
        val sizeMinuteHand = typedArray.getDimension(R.styleable.MyWatch_sizeMinuteHand,250f)
        minuteHandSize = sizeMinuteHand
        val sizeHourHand = typedArray.getDimension(R.styleable.MyWatch_sizeHourHand,200f)
        hourHandSize = sizeHourHand

        typedArray.recycle()
    }

    private fun init(){
        secondHandPaint = Paint().apply {
            strokeWidth = context.resources.getDimension(R.dimen.watch_strokeWidth)
        }
        minuteHandPaint= Paint().apply {
            strokeWidth = context.resources.getDimension(R.dimen.watch_strokeWidth)
        }
        hourHandPaint = Paint().apply {
            strokeWidth = context.resources.getDimension(R.dimen.watch_strokeWidth)
        }
    }

}





















