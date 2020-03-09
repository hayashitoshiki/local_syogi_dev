package com.example.local_syogi.presentation.presenter

import com.example.local_syogi.domain.AuthenticationUseCase
import com.example.local_syogi.presentation.contact.SettingAccountContact

class SettingAccountPresenter(private val view: SettingAccountContact.View, private val firebase:AuthenticationUseCase) :
    SettingAccountContact.Presenter {

    //ログイン認証
    override fun onStart(){
        firebase.firstCheck({
            view.setInformationView()
        },{
            view.setLoginView()
        })
    }

    override fun signIn(email:String, password:String){
        if(email != "" && password != ""){
            firebase.signIn(email,password,{
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
            view.signOut()
        },{
            view.showErrorToast()
        })
    }

    //新規作成処理
    override fun signUp(email:String, password:String){
        if(email != "" && password != "") {
            firebase.signUp(email, password, {
                view.setInformationView()
            }, {
                view.showErrorToast()
            })
        }else {
            view.showErrorEmailPassword()
        }
    }

}