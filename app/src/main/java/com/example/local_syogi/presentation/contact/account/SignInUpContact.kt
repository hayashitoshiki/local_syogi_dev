package com.example.local_syogi.presentation.contact.account

interface SignInUpContact {
    interface View {
        fun setInformationView()
        // エラー表示(Email or Password)
        fun showErrorEmailPassword()
        // エラートースト表示
        fun showErrorToast()
        // Email不備
        fun showErrorEmailToast()
        // ユーザー名不備
        fun showErrorUserNameToast()
        // パスワード不備
        fun showErrorPasswordToast()
        // パスワード不一致
        fun showErrorPasswordEqualToast()
    }

    interface Presenter {
        // ログイン
        fun signIn(email: String, password: String)
        // ユーザーのIDを取得
        fun getUid(): String
        // 新規作成処理
        fun signUp(userName: String, email: String, password: String, password2: String)
    }
}
