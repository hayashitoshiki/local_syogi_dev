package com.example.local_syogi.presentation.presenter.account

import com.example.local_syogi.data.entity.AccountEntity
import com.example.local_syogi.domain.AccountUseCase
import com.example.local_syogi.domain.model.FollowModel
import com.example.local_syogi.presentation.contact.account.AccountFollowContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AccountFollowPresenter(private val view: AccountFollowContact.View, private val accountUseCase: AccountUseCase) :
    AccountFollowContact.Presenter {

    // 友達リストを取得
    override fun getFollowList(): List<FollowModel> {
        val followList = listOf(
            FollowModel("友達一覧", 0),
            FollowModel("テスト1", 1),
            FollowModel("テスト2", 1),
            FollowModel("テスト3", 1),
            FollowModel("テスト4", 1),
            FollowModel("テスト5", 1),
            FollowModel("テスト6", 1),
            FollowModel("テスト7", 1))
        accountUseCase.findFollowByUserId("BBB", { test() }, { test() })
        accountUseCase.createFollow("AAA", "DDD", { test() }, { test() })
        accountUseCase.updateFollow("AAA", "DDD", { test() }, { test() })
        accountUseCase.deleteFollow("AAA", "DDD", { test() }, { test() })
        return followList
    }
    fun test() {
    }

    // アカウント検索処理
    override fun findAccount(userId: String, callBack: (accountList: List<FollowModel>) -> Unit) {
        accountUseCase.findAccountByUserId(userId, {
            callBack(it)
        }, {
            // 見つからなかった時
        })
    }

    // 承認待ちリスト取得
    override fun getFollowRequestList(): List<FollowModel> {
        val followList = listOf(
            FollowModel("リクエスト", 0),
            FollowModel("テスト8", 2))
        return followList
    }

    // 申請待ちリスト取得
    override fun getFollowRequestMeList(): List<FollowModel> {
        val followList = listOf(
            FollowModel("承認待ち", 0),
            FollowModel("テスト4", 3),
            FollowModel("テスト5", 3),
            FollowModel("テスト6", 3))
        return followList
    }
}