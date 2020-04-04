package com.example.local_syogi.syogibase.presentation.view

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * ゲーム選択画面の詳細設定記憶クラス
 */

class GameSettingSharedPreferences(
    private val context: Context,
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
) {

    fun setHandyBlack(mode: Int) {
        val editor = prefs.edit()
        editor.putInt("handyBlack", mode)
        editor.apply()
    }

    fun setHandyWhite(mode: Int) {
        val editor = prefs.edit()
        editor.putInt("handyWhite", mode)
        editor.apply()
    }

    fun getHandyBlack(): Int {
        return prefs.getInt("handyBlack", 0)
    }
    fun getHandyWhite(): Int {
        return prefs.getInt("handyWhite", 0)
    }
}