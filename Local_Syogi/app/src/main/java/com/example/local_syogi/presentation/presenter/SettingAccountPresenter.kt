package com.example.local_syogi.presentation.presenter

import com.example.local_syogi.domain.AuthenticationUseCase
import com.example.local_syogi.presentation.contact.SettingAccountContact

class SettingAccountPresenter(private val view: SettingAccountContact.View, private val firebase:AuthenticationUseCase) :
    SettingAccountContact.Presenter {

    private var session = false

    //ログイン状態を返す
    override fun checkSession():Boolean{
        return session
    }
    //自動ログイン認証
    override fun onStart(){
        firebase.firstCheck({
            session = true
            //view.setInformationView()
        },{
            view.setLoginViewFirst()
        })
    }

    //ログイン認証
    override fun signIn(email:String, password:String){
        if(email != "" && password != ""){
            firebase.signIn(email,password,{
                session = true
                view.setInformationView()
            },{
                view.showErrorToast()
            })
        } else{
            view.showErrorEmailPassword()
        }
    }

    //ログアウト
    override fun signOut() {
        firebase.signOut({
            session = false
            view.signOut()
        },{
            view.showErrorToast()
        })
    }

    //新規作成処理
    override fun signUp(email:String, password:String){
        if(email != "" && password != "") {
            firebase.signUp(email, password, {
                session = true
                view.setInformationView()
            }, {
                view.showErrorToast()
            })
        }else {
            view.showErrorEmailPassword()
        }
    }

}