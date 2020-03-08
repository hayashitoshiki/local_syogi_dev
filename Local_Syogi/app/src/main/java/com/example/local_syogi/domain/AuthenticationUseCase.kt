package com.example.local_syogi.domain

interface AuthenticationUseCase {
    //ログイン状態チェック
    fun firstCheck(onSuccess: () -> Unit, onError: () -> Unit)
    //ログイン
    fun signIn(email: String, password: String, onSuccess: () -> Unit, onError: () -> Unit)
    //ログアウト
    fun signOut(onSuccess: () -> Unit, onError: () -> Unit)
    //アカウント作成
    fun signUp(email:String, password:String, onSuccess: () -> Unit, onError: () -> Unit)
    //アカウント削除
    fun delete(onSuccess: () -> Unit, onError: () -> Unit)
    //ユーザーのEmailを取得
    fun getEmail():String
}