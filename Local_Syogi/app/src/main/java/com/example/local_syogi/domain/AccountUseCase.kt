package com.example.local_syogi.domain

import com.example.local_syogi.domain.model.FollowModel

interface AccountUseCase {
    // ユーザー登録
    fun createAccount(userId: String, userName: String, onSuccess: () -> Unit, onError: () -> Unit)
    // ユーザー検索
    fun findAccountByUserId(userId1: String, userId2: String, onSuccess: (accountList: List<FollowModel>) -> Unit, onError: () -> Unit)

    /*----------------------------------------
        フォロー
     ----------------------------------------*/
    // フォロー関係作成
    fun createFollow(userId1: String, userId2: String, onSuccess: () -> Unit, onError: () -> Unit)
    // フォロー中ユーザー取得
    fun findFollowByUserId(userId: String, onSuccess: (List<FollowModel>) -> Unit, onError: () -> Unit)
    // フォロー状態更新
    fun updateFollow(userId1: String, userId2: String, onSuccess: () -> Unit, onError: () -> Unit)
    // 　フォロー解除
    fun deleteFollow(userId1: String, userId2: String, onSuccess: () -> Unit, onError: () -> Unit)
}