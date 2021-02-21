package com.example.local_syogi.presentation.presenter.setting

import com.example.local_syogi.data.remote.FirebaseRepository
import com.example.local_syogi.presentation.contact.setting.SettingRootContact
import com.example.local_syogi.syogibase.util.IntUtil.FREE
import com.example.local_syogi.syogibase.util.IntUtil.RATE

class SettingRootPresenter(private val view: SettingRootContact.View, private val firebase: FirebaseRepository) : SettingRootContact.Presenter {

    private var mode = 1

    // Modeリセット(これを入れないとなぜか初期化されない)
    override fun initMode() {
        mode = FREE
    }

    override fun getMode(): Int {
        return mode
    }

    // ログイン状態判定
    override fun isAuth(): Boolean {
        return firebase.isAuth()
    }

    // モード変更
    private fun changeMode() {
        mode = if (mode == FREE) {
            RATE
        } else {
            FREE
        }
    }

    // タッチイベント
    override fun onTouchEvent(x: Int, y: Int, x2: Int, y2: Int) {
        if (x <= 400) {
            if (x2 - x < -10) {
                if (y in 800..1200) {
                    // Main
                    view.backHome()
                } else {
                    changeMode()
                    view.flipCardRight(mode)
                }
            } else if (10 < x2 - x) {
                changeMode()
                view.flipCardLeft(mode)
            }
        }
    }

    // 対局開始
    override fun startGame() {
        if (mode == FREE) {
            view.startNomarGame()
        } else if (isAuth()) {
            view.startRateGame()
        } else {
            view.showAuthToast()
        }
    }
}
