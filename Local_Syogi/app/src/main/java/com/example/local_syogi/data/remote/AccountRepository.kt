package com.example.local_syogi.data.remote

import com.example.local_syogi.data.remote.dto.AccountDto
import com.example.local_syogi.data.remote.dto.FollowDto
import com.example.local_syogi.data.remote.dto.StatusDto

interface AccountRepository {
    /*----------------------------------------
       ユーザー
    ----------------------------------------*/
    // ユーザー登録
    fun createAccount(userId: String, userName: String, onSuccess: (state: StatusDto) -> Unit, onError: () -> Unit)
    // ユーザー検索
    fun findAccountByUserId(userId: String, onSuccess: (userList: AccountDto) -> Unit, onError: () -> Unit)

    /*----------------------------------------
        フォロー
     ----------------------------------------*/
    // フォロー関係作成
    fun createFollow(userId1: String, userId2: String, onSuccess: (state: StatusDto) -> Unit, onError: () -> Unit)
    // フォロー中ユーザー取得
    fun findFollowByUserId(userId: String, onSuccess: (userList: FollowDto) -> Unit, onError: () -> Unit)
    // フォロー状態更新
    fun updateFollow(userId1: String, userId2: String, onSuccess: (state: StatusDto) -> Unit, onError: () -> Unit)
    // 　フォロー解除
    fun deleteFollow(userId1: String, userId2: String, onSuccess: (state: StatusDto) -> Unit, onError: () -> Unit)
}