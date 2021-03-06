package com.example.local_syogi.presentation.contact.account

import com.example.local_syogi.domain.model.FollowModel

interface AccountFollowContact {
    interface View {
        // 検索リストリセット
        fun resetSearchList()
        // フォローリスト更新
        fun updateFollowList(followList: List<FollowModel>)
    }

    interface Presenter {
        // 友達リストを取得
        fun getFollowList(callBack: (followList: List<FollowModel>) -> Unit)
        // アカウント検索処理
        fun findAccount(userId: String, callBack: (accountList: List<FollowModel>) -> Unit)
        // フォローアカウント検索処理
        fun addFollow(userId: String)
        // フォローアカウント削除処理
        fun deleteFollow(userId: String)
        // フォローアカウント認証処理
        fun updateFollow(userId: String)
        // ユーザーのIDを取得
        fun getUid(): String
    }
}
