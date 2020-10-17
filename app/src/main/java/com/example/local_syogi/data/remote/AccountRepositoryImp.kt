package com.example.local_syogi.data.remote

import android.util.Log
import com.example.local_syogi.data.entity.AccountEntity
import com.example.local_syogi.data.entity.FollowEntity
import com.example.local_syogi.data.remote.api.Provider
import com.example.local_syogi.data.remote.dto.AccountDto
import com.example.local_syogi.data.remote.dto.FollowDto
import com.example.local_syogi.data.remote.dto.StatusDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AccountRepositoryImp : AccountRepository {

    companion object {
        const val TAG = "Accountロジック"
    }

    // ユーザー登録
    override fun createAccount(userId: String, userName: String, callBack: (state: StatusDto) -> Unit, onError: () -> Unit) {
        val account = AccountEntity(userId, userName)
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val state = Provider.testApi().createAccount(account)
                callBack(state)
            } catch (e: Exception) {
                Log.d(TAG, "createAccount：Repository:Exception：" + e)
                onError()
            }
        }
    }

    // ユーザー検索
    override fun findAccountByUserId(userId: String, onSuccess: (userList: AccountDto) -> Unit, onError: () -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val userList = Provider.testApi().findAccount(userId)
                onSuccess(userList)
            } catch (e: Exception) {
                Log.d(TAG, "findAccountByUserId：Repository:Exception：" + e)
                onError()
            }
        }
    }

    // フォロー関係作成
    override fun createFollow(userId1: String, userId2: String, onSuccess: (state: StatusDto) -> Unit, onError: () -> Unit) {
        val follow = FollowEntity("", userId1, "", userId2, 1)
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val state = Provider.testApi().createFollow(follow)
                onSuccess(state)
            } catch (e: Exception) {
                Log.d(TAG, "createFollow：Repository:Exception：" + e)
                onError()
            }
        }
    }

    // フォロー中ユーザー取得
    override fun findFollowByUserId(userId: String, onSuccess: (userList: FollowDto) -> Unit, onError: () -> Unit) {
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val userList = Provider.testApi().findFollow(userId)
                onSuccess(userList)
            } catch (e: Exception) {
                Log.d(TAG, "findFollowByUserId：Repository:Exception：" + e)
                onError()
            }
        }
    }

    // フォロー状態更新
    override fun updateFollow(userId1: String, userId2: String, onSuccess: (state: StatusDto) -> Unit, onError: () -> Unit) {
        val follow = FollowEntity("", userId1, "", userId2, 1)
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val state = Provider.testApi().updateFollow(follow)
                onSuccess(state)
            } catch (e: Exception) {
                Log.d(TAG, "updateFollow：Repository:Exception：" + e)
                onError()
            }
        }
    }

    // フォロー解除
    override fun deleteFollow(userId1: String, userId2: String, onSuccess: (state: StatusDto) -> Unit, onError: () -> Unit) {
        val follow = FollowEntity("", userId1, "", userId2, 1)
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val state = Provider.testApi().deleteFollow(follow)
                onSuccess(state)
            } catch (e: Exception) {
                Log.d(TAG, "deleteFollow：Repository:Exception：" + e)
                onError()
            }
        }
    }
}