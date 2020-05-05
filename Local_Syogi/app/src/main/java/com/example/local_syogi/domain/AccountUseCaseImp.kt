package com.example.local_syogi.domain

import android.util.Log
import com.example.local_syogi.data.entity.AccountEntity
import com.example.local_syogi.data.remote.AccountRepository
import com.example.local_syogi.domain.model.FollowModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AccountUseCaseImp(private val repository: AccountRepository) : AccountUseCase {

    companion object {
        const val TAG = "Accountロジック"
    }

    // ユーザー登録
    override fun createAccount(userId: String, userName: String, onSuccess: () -> Unit, onError: () -> Unit) {
        try {
            repository.createAccount(userId, userName, {
                onSuccess()
            }, {
                Log.d(TAG, "取得失敗")
            })
        } catch (e: Exception) {
            Log.d(TAG, "createAccount：Exception：" + e)
        }
    }
    // ユーザー検索
    override fun findAccountByUserId(userId: String, onSuccess: (accountList: List<FollowModel>) -> Unit, onError: () -> Unit) {
        try {
            repository.findAccountByUserId(userId, {
                val accountList = arrayListOf<FollowModel>()
                it.data.forEach{
                    accountList.add(
                        FollowModel(
                            it.userName,
                            4
                        ))
                }
                accountList.toList()
                onSuccess(accountList)
            }, {
                Log.d(TAG, "取得失敗")
            })
        } catch (e: Exception) {
            Log.d(TAG, "findAccountByUserId:UseCase：Exception：" + e)
        }
    }

    // フォロー関係作成
    override fun createFollow(userId1: String, userId2: String, onSuccess: () -> Unit, onError: () -> Unit) {
        try {
            repository.createFollow(userId1, userId2, {
                onSuccess()
            }, {
                Log.d(TAG, "取得失敗")
            })
        } catch (e: Exception) {
            Log.d(TAG, "createFollow:UseCase：Exception：" + e)
        }

    }
    // フォロー中ユーザー取得
    override fun findFollowByUserId(userId: String, onSuccess: (List<FollowModel>) -> Unit, onError: () -> Unit) {
        try {
            repository.findFollowByUserId(userId, {
                val followModel = arrayListOf<FollowModel>()
                it.follow.forEach{
                    val userName = if(userId == it.userId1){
                        it.userId2
                    }else{
                        it.userId1
                    }
                    val state = when(it.state){
                        1 -> if(userId == it.userId1) {
                            1
                        }else{
                            2
                        }
                        2 -> 3
                        else -> 10
                    }
                    followModel.add(FollowModel(userName, state))
                }
                followModel.add(FollowModel("友達", 0))
                followModel.add(FollowModel("承認待ち", 0))
                followModel.add(FollowModel("リクエスト", 0))
                followModel.sortBy { it.status }
                onSuccess(followModel.toList())
            }, {
                Log.d(TAG, "取得失敗")
            })
        } catch (e: Exception) {
            Log.d(TAG, "findFollowByUserId：UseCase:Exception：" + e)
        }
    }
    // フォロー状態更新
    override fun updateFollow(userId1: String, userId2: String, onSuccess: () -> Unit, onError: () -> Unit) {
        try {
            repository.updateFollow(userId1, userId2, {
            }, {
                Log.d(TAG, "取得失敗")
            })
        } catch (e: Exception) {
            Log.d(TAG, "updateFollow：UseCase:Exception：" + e)
        }
    }
    //　フォロー解除
    override fun deleteFollow(userId1: String, userId2: String, onSuccess: () -> Unit, onError: () -> Unit) {
        try {
            repository.deleteFollow(userId1, userId2, {
                onSuccess()
            }, {
                Log.d(TAG, "取得失敗")
            })
        } catch (e: Exception) {
            Log.d(TAG, "deleteFollow：UseCase:Exception：" + e)
        }
    }
}