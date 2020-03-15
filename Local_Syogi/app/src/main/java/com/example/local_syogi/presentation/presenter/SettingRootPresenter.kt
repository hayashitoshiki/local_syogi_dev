package com.example.local_syogi.presentation.presenter

import com.example.local_syogi.data.FirebaseRepository
import com.example.local_syogi.presentation.contact.SettingRootContact

class SettingRootPresenter(private val view:SettingRootContact.View, private val firebase:FirebaseRepository): SettingRootContact.Presenter {

    //ログイン状態判定
    override fun isAuth(): Boolean {
        return firebase.isAuth()
    }
}