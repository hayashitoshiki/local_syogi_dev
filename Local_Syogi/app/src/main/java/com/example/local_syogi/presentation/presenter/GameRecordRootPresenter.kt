package com.example.local_syogi.presentation.presenter

import com.example.local_syogi.domain.AuthenticationUseCase
import com.example.local_syogi.presentation.contact.GameRecordRootContact

class GameRecordRootPresenter(private val view: GameRecordRootContact.View, private val firebase: AuthenticationUseCase) :
    GameRecordRootContact.Presenter {

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

    // ログイン認証
    override fun signIn(email: String, password: String) {
        if (email != "" && password != "") {
            firebase.signIn(email, password, {
                view.setInformationView()
            }, {
                view.showErrorToast()
            })
        } else {
            view.showErrorEmailPassword()
        }
    }

    // ログアウト
    override fun signOut() {
        firebase.signOut({
            view.signOut()
        }, {
            view.showErrorToast()
        })
    }

    // 新規作成処理
    override fun signUp(email: String, password: String) {
        if (email != "" && password != "") {
            firebase.signUp(email, password, {
                view.setInformationView()
            }, {
                view.showErrorToast()
            })
        } else {
            view.showErrorEmailPassword()
        }
    }
}