package com.zwy.kalimba.view

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import com.zwy.kalimba.R
import kotlinx.android.synthetic.main.item_key.view.*
import kotlin.math.abs

/**
 * @author Afauria
 * @date 2020/8/7
 */
class KalimbaViewGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val soundPool = SoundPool(5, AudioManager.STREAM_MUSIC, 5)

    /**
     * 列表从中央C开始，C在键盘中间位置
     * 列表索引对应到键盘位置：getPositionByIndex
     * 键盘位置对应到列表索引：getIndexByPosition
     *
     * 例如C4对应键盘位置：getPositionByIndex(0) = 8
     * 例如键盘位置8对应C4：getIndexByPosition(8) = 0
     * todo 提高效率，固定map映射
     */
    private val mKalimbaKeys = listOf(
        KalimbaKey("C4", "1", R.raw.c4),
        KalimbaKey("D4", "2", R.raw.d4),
        KalimbaKey("E4", "3", R.raw.e4),
        KalimbaKey("F4", "4", R.raw.f4),
        KalimbaKey("G4", "5", R.raw.g4),
        KalimbaKey("A4", "6", R.raw.a4),
        KalimbaKey("B4", "7", R.raw.b4),
        KalimbaKey("C5", "1+", R.raw.c5),
        KalimbaKey("D5", "2+", R.raw.d5),
        KalimbaKey("E5", "3+", R.raw.e5),
        KalimbaKey("F5", "4+", R.raw.f5),
        KalimbaKey("G5", "5+", R.raw.g5),
        KalimbaKey("A5", "6+", R.raw.a5),
        KalimbaKey("B5", "7+", R.raw.b5),
        KalimbaKey("C6", "1++", R.raw.c6),
        KalimbaKey("D6", "2++", R.raw.e6),
        KalimbaKey("E6", "3++", R.raw.d6)
    )
    private var mCurrentIndex = 0
        set(value) {
            if (value < 0 || value > mKalimbaKeys.size - 1) {
                return
            }
            field = value
            val kalimbaKey = mKalimbaKeys[value]
            soundPool.play(kalimbaKey.soundId, 1f, 1f, 0, 0, 1f)
            onPressKeyChanged?.invoke(mCurrentIndex)
        }

    private var mCurrentPosition = 0
        set(value) {
            if (value < 0 || value > mKalimbaKeys.size - 1) {
                return
            }
            field = value
        }
    var onPressKeyChanged: ((i: Int) -> Unit)? = null

    init {
        visibility = View.GONE
        mKalimbaKeys.forEachIndexed { pos, value ->
            val view = LayoutInflater.from(context).inflate(R.layout.item_key, this, false)
            val lp = view.layoutParams
            lp.height = calculateHeight(pos)
            view.layoutParams = lp
            val kalimbaKey = mKalimbaKeys[getIndexByPosition(pos)]
            view.keyName.text = kalimbaKey.keyName
            view.keyNum.text = kalimbaKey.keyNum
            addView(view)
            kalimbaKey.soundId = soundPool.load(context, kalimbaKey.resId, 1)
        }

        soundPool.setOnLoadCompleteListener { soundPool, sampleId, status ->
            if (sampleId == 7) {
                visibility = View.VISIBLE
            }
        }
    }

    private val mGesture =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onDown(e: MotionEvent): Boolean {
                for (i in 0 until childCount) {
                    val v = getChildAt(i)
                    val pressed = isTouchPointInView(v, e.x, e.y)
                    if (v.isPressed != pressed) {
                        v.isPressed = pressed
                        if (pressed) {
                            mCurrentPosition = i
                            mCurrentIndex = getIndexByPosition(i)
                        }
                    }
                }
                return super.onDown(e)
            }

            override fun onScroll(
                e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float
            ): Boolean {
                for (i in 0 until childCount) {
                    val v = getChildAt(i)
                    val pressed = isTouchPointInView(v, e2.x, e2.y)
                    if (v.isPressed != pressed) {
                        v.isPressed = pressed
                        if (pressed) {
                            mCurrentPosition = i
                            mCurrentIndex = getIndexByPosition(i)
                        }
                    }
                }
                return true
            }
        })


    override fun onTouchEvent(event: MotionEvent): Boolean {
        mGesture.onTouchEvent(event)
        if (event.action == MotionEvent.ACTION_UP) {
            getChildAt(mCurrentPosition).isPressed = false
        }
        return true
    }

    private fun isTouchPointInView(v: View, _x: Float, _y: Float): Boolean =
        (_y >= v.top && _y <= v.bottom && _x >= v.left && _x <= v.right)

    private fun calculateHeight(index: Int): Int {
        return 700 - abs(mKalimbaKeys.size / 2 - index) * 30
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val index = when (keyCode) {
            KeyEvent.KEYCODE_A -> 0
            KeyEvent.KEYCODE_S -> 1
            KeyEvent.KEYCODE_D -> 2
            KeyEvent.KEYCODE_F -> 3
            KeyEvent.KEYCODE_G -> 4
            KeyEvent.KEYCODE_H -> 5
            KeyEvent.KEYCODE_J -> 6
            KeyEvent.KEYCODE_Q -> 7
            KeyEvent.KEYCODE_W -> 8
            KeyEvent.KEYCODE_E -> 9
            KeyEvent.KEYCODE_R -> 10
            KeyEvent.KEYCODE_T -> 11
            KeyEvent.KEYCODE_Y -> 12
            KeyEvent.KEYCODE_U -> 13
            KeyEvent.KEYCODE_I -> 14
            KeyEvent.KEYCODE_O -> 15
            KeyEvent.KEYCODE_P -> 16
            else -> -1
        }
        mCurrentIndex = index
        mCurrentPosition = getPositionByIndex(index)
        getChildAt(mCurrentPosition).isPressed = true
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        getChildAt(mCurrentPosition).isPressed = false
        return true
    }

    //根据数组index计算该键的position
    private fun getPositionByIndex(index: Int): Int {
        //x为偶数：position = 8 + index / 2
        //x为奇数：position = 8 - index / 2 - 1
        return if (index % 2 == 0) {
            mKalimbaKeys.size / 2 + index / 2
        } else {
            mKalimbaKeys.size / 2 - 1 - index / 2
        }
    }

    private fun getIndexByPosition(position: Int): Int {
        //x为偶数：index = (position - 8) * 2
        //x为奇数：index = (8 - position) * 2 - 1
        return if (position >= mKalimbaKeys.size / 2) {
            //右半边index都是偶数
            (position - mKalimbaKeys.size / 2) * 2
        } else {
            //左半边index都是奇数，-1
            (mKalimbaKeys.size / 2 - position) * 2 - 1
        }
    }
}

class KalimbaKey(val keyName: String, val keyNum: String, val resId: Int = R.raw.c4) {
    var soundId: Int = 0
}