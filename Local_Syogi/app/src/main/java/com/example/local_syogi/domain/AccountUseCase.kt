package com.example.local_syogi.domain

interface AccountUseCase {
    // ユーザー登録
    fun createAccount(userId: String, userName: String, onSuccess: () -> Unit, onError: () -> Unit)
    // ユーザー検索
    fun findAccountByUserId(userId: String, onSuccess: () -> Unit, onError: () -> Unit)

    /*----------------------------------------
        フォロー
     ----------------------------------------*/
    // フォロー関係作成
    fun createFollow(userId1: String, userId2: String, onSuccess: () -> Unit, onError: () -> Unit)
    // フォロー中ユーザー取得
    fun findFollowByUserId(userId: String, onSuccess: () -> Unit, onError: () -> Unit)
    // フォロー状態更新
    fun updateFollow(userId1: String, userId2: String, onSuccess: () -> Unit, onError: () -> Unit)
    // 　フォロー解除
    fun deleteFollow(userId1: String, userId2: String, onSuccess: () -> Unit, onError: () -> Unit)
}