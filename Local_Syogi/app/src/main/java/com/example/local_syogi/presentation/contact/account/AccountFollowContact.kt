package com.example.local_syogi.presentation.contact.account

import com.example.local_syogi.data.entity.AccountEntity
import com.example.local_syogi.domain.model.FollowModel

interface AccountFollowContact {
    interface View

    interface Presenter {
        // 友達リストを取得
        fun getFollowList(): List<FollowModel>
        // 承認待ちリスト取得
        fun getFollowRequestList(): List<FollowModel>
        // 申請待ちリスト取得
        fun getFollowRequestMeList(): List<FollowModel>
        // アカウント検索処理
        fun findAccount(userId: String, callBack: (accountList: List<FollowModel>) -> Unit)
    }
}