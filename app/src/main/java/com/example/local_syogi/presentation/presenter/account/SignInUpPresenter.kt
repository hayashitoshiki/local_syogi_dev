package com.example.local_syogi.presentation.presenter.account

import android.util.Log
import com.example.local_syogi.domain.AccountUseCase
import com.example.local_syogi.domain.AccountUseCaseImp
import com.example.local_syogi.domain.AuthenticationUseCase
import com.example.local_syogi.presentation.contact.account.SignInUpContact

class SignInUpPresenter(val view: SignInUpContact.View, private val auth: AuthenticationUseCase, private val account: AccountUseCase) : SignInUpContact.Presenter {

    // ログイン認証
    override fun signIn(email: String, password: String) {
        if (email != "" && password != "") {
            auth.signIn(email, password, {
                view.setInformationView()
            }, {
                view.showErrorToast()
            })
        } else {
            view.showErrorEmailPassword()
        }
    }

    // 新規作成処理
    override fun signUp(userName: String, email: String, password: String, password2: String) {
        when {
            userName == "" -> {
                view.showErrorUserNameToast()
            }
            email == "" -> {
                view.showErrorEmailToast()
            }
            password == "" -> {
                view.showErrorPasswordToast()
            }
            password != password2 -> {
                view.showErrorPasswordEqualToast()
            }
            else -> {
                // 新規作成
                auth.signUp(email, password, userName, {
                    account.createAccount(auth.getUid(), userName, {
                        Log.d(AccountUseCaseImp.TAG, "createAccount：")
                        view.setInformationView()
                    }, {
                        view.showErrorToast()
                    })
                }, {
                    view.showErrorToast()
                })
            }
        }
    }

    // ユーザーのIDを取得
    override fun getUid(): String {
        return auth.getUid()
    }
}
