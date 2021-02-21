package com.example.local_syogi.presentation.contact.account

interface AuthenticationBaseContact {

    interface View
    interface Presenter {
        // ログイン状態判定
        fun isSession(): Boolean
    }
}
