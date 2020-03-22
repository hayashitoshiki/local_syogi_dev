package com.example.local_syogi.presentation.contact

interface SettingRootContact {
    interface View {
        // ホーム画面へ戻る
        fun backHome()
        // 「モード選択」(タイトル)Viewを非表示にする
        fun firstChoice()
        // タブカード回転
        fun flipCardRight(mode: Int)
        // タブカード回転
        fun flipCardLeft(mode: Int)
        // 通常対戦画面へ遷移
        fun startNomarGame()
        // 通信対戦画面へ遷移
        fun startRateGame()
        // エラー表示
        fun showAuthToast()
    }

    interface Presenter {
        // ログイン認証
        fun isAuth(): Boolean
        // タッチイベント
        fun onTouchEvent(x: Int, y: Int, x2: Int, y2: Int)
        // 対局開始
        fun startGame()
    }
}