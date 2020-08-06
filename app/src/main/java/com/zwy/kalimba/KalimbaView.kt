package com.zwy.kalimba

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * @author Afauria
 * @date 2020/8/6
 */
class KalimbaView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mItemWidth = 0
    private var mScreenWidth = 0
    private val mKeyNames = listOf("C4","D4","E4","F4","G4","A4","B4","C5","D5","E5","F5","G5","A5","B5","C6","D6","E6")
    private lateinit var mStripRect:Rect
    private val mPaint = Paint().apply {

    }

    init {
        mScreenWidth = context.resources.displayMetrics.widthPixels
        mItemWidth = mScreenWidth / mKeyNames.size
        mStripRect = Rect(0,10,mScreenWidth,50)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(mStripRect, mPaint)
        canvas.drawRoundRect(RectF(0f,0f,50f,100f),50f,50f,mPaint)
    }

}