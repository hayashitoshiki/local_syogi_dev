package com.example.local_syogi.domain

import android.util.Log
import com.example.local_syogi.data.remote.AccountRepository
import com.example.local_syogi.domain.model.FollowModel

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
    override fun findAccountByUserId(userId1: String, userId2: String, onSuccess: (accountList: List<FollowModel>) -> Unit, onError: () -> Unit) {
        try {
            repository.findAccountByUserId(userId2, {
                val accountList = arrayListOf<FollowModel>()
                it.data.forEach {
                    if (it.userId != userId1) {
                        accountList.add(
                            FollowModel(
                                it.userName,
                                it.userId,
                                41
                            )
                        )
                    }
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
                it.follow.forEach { followEntity ->
                    val (userName: String, userId: String) = if (userId == followEntity.userId1) {
                        Pair(followEntity.userName2, followEntity.userId2)
                    } else {
                        Pair(followEntity.userName1, followEntity.userId1)
                    }
                    val state = when (followEntity.state) {
                        1 -> if (userId == followEntity.userId1) {
                            21
                        } else {
                            31
                        }
                        2 -> 11
                        else -> 100
                    }
                    followModel.add(FollowModel(userName, userId, state))
                }
                followModel.add(FollowModel("友達", "", 10))
                followModel.add(FollowModel("承認待ち", "", 20))
                followModel.add(FollowModel("リクエスト", "", 30))
                followModel.sortBy { it2 -> it2.status }
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
                onSuccess()
            }, {
                Log.d(TAG, "取得失敗")
            })
        } catch (e: Exception) {
            Log.d(TAG, "updateFollow：UseCase:Exception：" + e)
        }
    }
    // 　フォロー解除
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