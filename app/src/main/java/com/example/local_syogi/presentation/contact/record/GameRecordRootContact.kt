package com.example.local_syogi.presentation.contact.record

interface GameRecordRootContact {
    interface View {
        // 正気状態でログイン画面を表示する
        fun setLoginViewFirst()
        // ログイン画面を表示する
        fun setLoginView()
        // ユーザー情報画面を表示する
        fun setInformationView()
        // エラー表示(Email or Password)
        fun showErrorEmailPassword()
        // エラートースト表示
        fun showErrorToast()
        // ログアウトトースト表示
        fun signOut()
    }

    interface Presenter {
        // ログイン状態判定
        fun isSession(): Boolean
        // ログイン認証
        fun onStart()
        // ログイン
        fun signIn(email: String, password: String)
        // ログアウト
        fun signOut()
        // 新規作成処理
        fun signUp(email: String, password: String, userName: String)
    }
}
