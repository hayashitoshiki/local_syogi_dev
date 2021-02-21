package com.example.local_syogi.presentation.presenter.account

import com.example.local_syogi.domain.AuthenticationUseCase
import com.example.local_syogi.presentation.contact.account.SignOutContact

class SignOutPresenter(val view: SignOutContact.View, private val auth: AuthenticationUseCase) : SignOutContact.Presenter {

    // 自動ログイン認証
    override fun onStart() {
        auth.firstCheck({}, {})
    }
    // ログアウト
    override fun signOut() {
        auth.signOut({
            view.signOut()
        }, {
            view.showErrorToast()
        })
    }
    // ユーザーのIDを取得
    override fun getUid(): String {
        return auth.getUid()
    }

    // ユーザー名を取得
    override fun getUserName(): String {
        return auth.getUserName()
    }

    // ユーザーのemail取得
    override fun getEmail(): String {
        return auth.getEmail()
    }
}
