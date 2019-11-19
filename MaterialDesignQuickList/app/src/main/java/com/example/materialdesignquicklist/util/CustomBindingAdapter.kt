package com.example.materialdesignquicklist.util

/*
 * レイアウトファイル内の"android:background"など
 * Databindingで使用出来ないものを使えるようにするための拡張用クラス
 */

import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter

class CustomBindingAdapter {
    companion object {

        @JvmStatic
        @BindingAdapter("android:background")
        fun setBackgroundColor(view: View, color: String) {
            if ((view is TextView || view is LinearLayout ) && color != ""){
                view.setBackgroundColor(Color.parseColor(color))
            }
        }

    }
}