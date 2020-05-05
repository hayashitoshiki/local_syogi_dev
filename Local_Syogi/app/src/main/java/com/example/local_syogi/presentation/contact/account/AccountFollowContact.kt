package com.example.local_syogi.presentation.contact.account

import com.example.local_syogi.data.entity.AccountEntity
import com.example.local_syogi.domain.model.FollowModel

interface AccountFollowContact {
    interface View {
        // 検索リストリセット
        fun resetSearchList()
    }

    interface Presenter {
        // 友達リストを取得
        fun getFollowList(): List<FollowModel>
        // 承認待ちリスト取得
        fun getFollowRequestList(): List<FollowModel>
        // 申請待ちリスト取得
        fun getFollowRequestMeList(): List<FollowModel>
        // アカウント検索処理
        fun findAccount(userId: String, callBack: (accountList: List<FollowModel>) -> Unit)
        // アカウント検索処理
        fun addFollow(userId: String)
        // ユーザーのIDを取得
        fun getUid(): String
    }
}