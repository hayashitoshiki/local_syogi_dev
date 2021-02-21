package com.example.local_syogi.presentation.contact.account

interface SignOutContact {
    interface View {
        // ログアウト
        fun signOut()
        // エラートースト表示
        fun showErrorToast()
    }

    interface Presenter {
        // ログイン認証
        fun onStart()
        // ログアウト
        fun signOut()
        // ユーザーのIDを取得
        fun getUid(): String
        // ユーザー名を取得
        fun getUserName(): String
        // ユーザーのemail取得
        fun getEmail(): String
    }
}
