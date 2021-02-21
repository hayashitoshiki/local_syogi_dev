package com.example.local_syogi.data.remote

interface FirebaseRepository {

    // ログイン状態を返す(Boolean型)
    fun isAuth(): Boolean
    // ログイン状態チェック
    fun firstCheck(onSuccess: () -> Unit, onError: () -> Unit)
    // ログイン
    fun signIn(email: String, password: String, onSuccess: () -> Unit, onError: () -> Unit)
    // ログアウト
    fun signOut(onSuccess: () -> Unit, onError: () -> Unit)
    // アカウント作成
    fun signUp(email: String, password: String, onSuccess: () -> Unit, onError: () -> Unit)
    // ユーザー情報の更新
    fun update(userName: String, onSuccess: () -> Unit, onError: () -> Unit)
    // アカウント削除
    fun delete(onSuccess: () -> Unit, onError: () -> Unit)
    // ユーザーのEmailを取得
    fun getEmail(): String
    // ユーザーのUIDを返す
    fun getUid(): String
    // ユーザ名を返す
    fun getName(): String
}
