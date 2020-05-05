package com.example.local_syogi.presentation.presenter.account

import android.util.Log
import com.example.local_syogi.data.entity.AccountEntity
import com.example.local_syogi.domain.AccountUseCase
import com.example.local_syogi.domain.AccountUseCaseImp
import com.example.local_syogi.domain.AuthenticationUseCase
import com.example.local_syogi.domain.model.FollowModel
import com.example.local_syogi.presentation.contact.account.AccountFollowContact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AccountFollowPresenter(private val view: AccountFollowContact.View, private val accountUseCase: AccountUseCase,private val auth: AuthenticationUseCase) :
    AccountFollowContact.Presenter {

    // 友達リストを取得
    override fun getFollowList(callBack: (followList: List<FollowModel>) -> Unit) {
        accountUseCase.findFollowByUserId(getUid(), {
            callBack(it)
        }, {})
        accountUseCase.updateFollow("AAA", "DDD", { test() }, { test() })
        accountUseCase.deleteFollow("AAA", "DDD", { test() }, { test() })

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

    // アカウントフォロー処理
    override fun addFollow(userId: String) {
        accountUseCase.createFollow(getUid(), userId, {
            getFollowList{
                view.resetSearchList()
                view.updateFollowList(it)
            }
        }, {
            // フォローできなかった時
        })
    }

    // ユーザーのIDを取得
    override fun getUid(): String {
        return auth.getUid()
    }
}