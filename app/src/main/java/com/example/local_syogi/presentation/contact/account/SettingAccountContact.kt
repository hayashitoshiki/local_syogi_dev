package com.example.local_syogi.presentation.contact.account

interface SettingAccountContact {
    interface View {
        // 初期状態でログイン画面を表示する
        fun setLoginViewFirst()
        // ログイン画面を表示する
        fun setLoginView()
        // ユーザー情報画面を表示する
        fun setInformationView()
    }

    interface Presenter {
        // ログイン状態判定
        fun isSession(): Boolean
        // ログイン認証
        fun onStart()
    }
}
