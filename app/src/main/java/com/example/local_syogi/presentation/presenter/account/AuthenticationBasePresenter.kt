package com.example.local_syogi.presentation.presenter.account

import com.example.local_syogi.domain.AuthenticationUseCase
import com.example.local_syogi.presentation.contact.account.AuthenticationBaseContact

class AuthenticationBasePresenter(private val auth: AuthenticationUseCase) : AuthenticationBaseContact.Presenter {

    // ログイン状態を返す
    override fun isSession(): Boolean {
        return auth.isAuth()
    }
}
