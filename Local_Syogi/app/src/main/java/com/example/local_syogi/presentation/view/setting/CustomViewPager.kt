package com.example.local_syogi.presentation.view.setting

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * ViewPager拡張クラス
 * 　・スクロール調整(有無、位置)
 */

class CustomViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    private var isEnable = true
    var x: Int = 0
    var y: Int = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (isEnable) {
            true -> return super.onTouchEvent(event)
            else -> {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        super.onTouchEvent(event)
                        x = event.x.toInt()
                        y = event.y.toInt()
                        if (670 <= x && y in 160..250) {
                            return true
                        }
                        return false
                    }
                    else -> {
                        super.onTouchEvent(event)
                        return true
                    }
                }
            }
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean = when (isEnable) {
        true -> super.onInterceptTouchEvent(ev)
        else -> super.onInterceptTouchEvent(ev)
    }

    fun setPagingEnabled(isEnable: Boolean) {
        this.isEnable = isEnable
    }
}