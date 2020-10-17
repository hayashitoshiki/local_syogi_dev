package com.example.local_syogi.presentation.presenter.account

import com.example.local_syogi.domain.AuthenticationUseCase
import com.example.local_syogi.presentation.contact.account.SettingAccountContact

class SettingAccountPresenter(private val view: SettingAccountContact.View, private val firebase: AuthenticationUseCase) :
    SettingAccountContact.Presenter {

    // ログイン状態を返す
    override fun isSession(): Boolean {
        return firebase.isAuth()
    }

    // 自動ログイン認証
    override fun onStart() {
        firebase.firstCheck({
            // view.setInformationView()
        }, {
            view.setLoginViewFirst()
        })
    }
}