package com.example.local_syogi.data.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class FirebaseRepositoryImp :
    FirebaseRepository {

    companion object {
        private const val TAG = "FireBaseRepositoryImp"
    }

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // ユーザーのEmailを取得
    override fun getEmail(): String {
        return auth.currentUser!!.email!!
    }

    // ユーザーのUIDを返す
    override fun getUid(): String {
        return auth.currentUser!!.uid
    }

    // ユーザ名を返す
    override fun getName(): String {
        return auth.currentUser!!.displayName!!
    }

    // ログイン状態を返す(Boolean型)
    override fun isAuth(): Boolean {
        if (auth.currentUser != null) {
            return true
        }
        return false
    }

    // 自動ログイン認証(Callback型)
    override fun firstCheck(onSuccess: () -> Unit, onError: () -> Unit) {
        if (auth.currentUser != null) {
            onSuccess()
        } else {
            onError()
        }
    }

    // ログイン機能
    override fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "ログイン成功")
                    onSuccess()
                } else {
                    Log.d(TAG, "ログイン失敗", task.exception)
                    onError()
                }
            }
    }

    // ログアウト
    override fun signOut(onSuccess: () -> Unit, onError: () -> Unit) {
        auth.signOut()
        if (auth.currentUser == null) {
            Log.d(TAG, "ログアウト作成成功")
            onSuccess()
        } else {
            Log.d(TAG, "ログアウト失敗")
            onError()
        }
    }

    // アカウント作成
    override fun signUp(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "アカウント作成成功")
                    onSuccess()
                } else {
                    Log.w(TAG, "アカウント作成失敗", task.exception)
                    onError()
                }
            }
    }

    // ユーザー情報の更新
    override fun update(
        userName: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {

        val profileUpdates = UserProfileChangeRequest.Builder()
            .setDisplayName(userName) // ユーザー名
            .build()

        auth.currentUser?.updateProfile(profileUpdates)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "更新成功")
                    onSuccess()
                } else {
                    Log.d(TAG, "更新失敗", task.exception)
                    onError()
                }
            }
    }

    // ユーザー削除
    override fun delete(onSuccess: () -> Unit, onError: () -> Unit) {
        auth.currentUser!!.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "ユーザーを削除しました")
                    onSuccess()
                } else {
                    Log.d(TAG, "予期せぬエラーが発生しました")
                    onError()
                }
            }
    }
}
