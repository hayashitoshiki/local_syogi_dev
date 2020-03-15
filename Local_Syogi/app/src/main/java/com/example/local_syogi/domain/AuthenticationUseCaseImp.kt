package com.example.local_syogi.domain

import com.example.local_syogi.data.FirebaseRepository

class AuthenticationUseCaseImp(private val firebaseRepository:FirebaseRepository):AuthenticationUseCase {

    //ログイン状態を返す
    override fun isAuth():Boolean{
        return firebaseRepository.isAuth()
    }
    //ログイン状態チェック
    override fun firstCheck(onSuccess: () -> Unit, onError: () -> Unit){
        firebaseRepository.firstCheck(
            {
                onSuccess()
            },{
                onError()
            })
    }

    //ログイン
    override fun signIn(email: String, password: String,onSuccess: () -> Unit, onError: () -> Unit){
        firebaseRepository.signIn(email,password,
            {
                onSuccess()
            },{
                onError()
            })
    }
    //ログアウト
    override fun signOut(onSuccess: () -> Unit, onError: () -> Unit){
        firebaseRepository.signOut(
            {
                onSuccess()
            },{
                onError()
            })
    }

    //アカウント作成
    override fun signUp(email:String, password:String,onSuccess: () -> Unit, onError: () -> Unit){
        firebaseRepository.signUp(email, password,
            {
                onSuccess()
            },{
                onError()
            })
    }

    //アカウント削除
    override fun delete(onSuccess: () -> Unit, onError: () -> Unit){
        firebaseRepository.delete(
            {
                onSuccess()
            },{
                onError()
            })
    }

    //ユーザーのEmailを取得
    override fun getEmail():String{
        return firebaseRepository.getEmail()
    }
}