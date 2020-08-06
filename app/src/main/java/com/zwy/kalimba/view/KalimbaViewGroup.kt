package com.zwy.kalimba.view

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import com.socks.library.KLog
import com.zwy.kalimba.R

/**
 * @author Afauria
 * @date 2020/8/7
 */
class KalimbaViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val mKeyNames = listOf("C4","D4","E4","F4","G4","A4","B4","C5","D5","E5","F5","G5","A5","B5","C6","D6","E6")

    private val mGesture =  GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {

            return true
        }

        override fun onScroll(
            e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float
        ): Boolean {

            return true
        }
    })
    init {
        mKeyNames.forEach {
            val view = LayoutInflater.from(context).inflate(R.layout.item_key,null,false)
            val itemWidth = 50
            val itemHeight = 100
            view.layoutParams = LayoutParams(itemWidth,itemHeight)
            addView(view)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }
}