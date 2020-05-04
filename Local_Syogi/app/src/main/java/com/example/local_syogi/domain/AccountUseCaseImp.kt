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
            Log.d(TAG, "findAccountByUserId_repository：Exception：" + e)
        }
    }

    // フォロー関係作成
    override fun createFollow(userId1: String, userId2: String, onSuccess: () -> Unit, onError: () -> Unit) {
        Log.d(TAG, "接続")
        GlobalScope.launch {
            try {
                repository.createFollow(userId1, userId2, {
                }, {
                    Log.d(TAG, "取得失敗")
                })
            } catch (e: Exception) {
                Log.d(TAG, "createFollow：Exception：" + e)
            }
        }
    }
    // フォロー中ユーザー取得
    override fun findFollowByUserId(userId: String, onSuccess: () -> Unit, onError: () -> Unit) {
        Log.d(TAG, "接続")
        GlobalScope.launch {
            try {
                repository.findFollowByUserId(userId, {
                }, {
                    Log.d(TAG, "取得失敗")
                })
            } catch (e: Exception) {
                Log.d(TAG, "findFollowByUserId：Exception：" + e)
            }
        }
    }
    // フォロー状態更新
    override fun updateFollow(userId1: String, userId2: String, onSuccess: () -> Unit, onError: () -> Unit) {
        Log.d(TAG, "接続")
        GlobalScope.launch {
            try {
                repository.updateFollow(userId1, userId2, {
                }, {
                    Log.d(TAG, "取得失敗")
                })
            } catch (e: Exception) {
                Log.d(TAG, "updateFollow：Exception：" + e)
            }
        }
    }
    // 　フォロー解除
    override fun deleteFollow(userId1: String, userId2: String, onSuccess: () -> Unit, onError: () -> Unit) {
        Log.d("findAccountBy", "接続")
        GlobalScope.launch {
            try {
                repository.deleteFollow(userId1, userId2, {
                }, {
                    Log.d(TAG, "取得失敗")
                })
            } catch (e: Exception) {
                Log.d(TAG, "deleteFollow：Exception：" + e)
            }
        }
    }
}