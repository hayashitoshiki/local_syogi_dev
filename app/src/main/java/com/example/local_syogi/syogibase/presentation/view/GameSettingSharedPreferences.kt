package com.example.local_syogi.syogibase.presentation.view

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * ゲーム選択画面の詳細設定記憶クラス
 *
 * SharedPreferences中身
 *   ・handyBlack ：先手番の駒落ち設定
 *   ・handyWhite ：後手番の駒落ち設定
 *   ・minuteBlack：先手番の持ち時間(分)
 *   ・secondBlack：先手番の持ち時間(秒)
 *   ・minuteWhite：後手番の持ち時間(分)
 *   ・secondBlack：先手番の持ち時間(秒)
 *   ・tryRule    :トライルールの有無(true or false)
 *   ・timeLimit  :レート対戦時の持ち時間
 *
 * 駒落ち設定詳細 (Int)
 *   1：なし
 *   2：香落ち
 *   3：角落ち
 *   4：飛車落ち
 *   5：2枚落ち
 *   6：4枚落ち
 *   7：6枚落ち
 *   8：8枚落ち
 *
 * 持ち時間
 *   ０～５９(分、秒)
 *
 * 持ち時間(レート)
 *   １０：3分切れ負け
 *   ２０：5分切れ負け
 *   ３０：10分切れ負け
 */

class GameSettingSharedPreferences(
    private val context: Context,
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
) {

    // 駒落ち記録
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

    // 持ち時間記録
    fun setMinuteBlack(minute: Int) {
        val editor = prefs.edit()
        editor.putInt("minuteBlack", minute)
        editor.apply()
    }
    fun setSecondBlack(second: Int) {
        val editor = prefs.edit()
        editor.putInt("secondBlack", second)
        editor.apply()
    }
    fun setMinuteWhite(minute: Int) {
        val editor = prefs.edit()
        editor.putInt("minuteWhite", minute)
        editor.apply()
    }
    fun setSecondWhite(second: Int) {
        val editor = prefs.edit()
        editor.putInt("secondWhite", second)
        editor.apply()
    }
    fun setRateTimeLimit(mode: Int) {
        val editor = prefs.edit()
        editor.putInt("timeLimit", mode)
        editor.apply()
    }
    // トライルール呼び出し
    fun setTryRule(status: Boolean) {
        val editor = prefs.edit()
        editor.putBoolean("tryRule", status)
        editor.apply()
    }

    // 駒落ち呼び出し
    fun getHandyBlack(): Int {
        return prefs.getInt("handyBlack", 0)
    }
    fun getHandyWhite(): Int {
        return prefs.getInt("handyWhite", 0)
    }

    // 持ち時間呼び出し
    fun getMinuteBlack(): Int {
        return prefs.getInt("minuteBlack", 10)
    }
    fun getSecondBlack(): Int {
        return prefs.getInt("secondBlack", 0)
    }
    fun getMinuteWhite(): Int {
        return prefs.getInt("minuteWhite", 10)
    }
    fun getSecondWhite(): Int {
        return prefs.getInt("secondWhite", 0)
    }
    fun getRateTimeLimit(): Int {
        return prefs.getInt("timeLimit", 3 * 60 * 1000)
    }
    // トライルール呼び出し
    fun getTryRule(): Boolean {
        return prefs.getBoolean("tryRule", true)
    }
}