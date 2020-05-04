package com.example.local_syogi.domain.model

/**
 * 友達リストモデル
 * userName：アカウント名
 * status  :取得状態
 *
 * status
 * 1・・・フォロワー
 * 2・・・承認待ち
 * 3・・・認証待ち
 */

class FollowModel(val userName: String, val status: Int) {
    fun getButtonName(): String {
        return when (status) {
            1 -> "削除"
            2 -> "承認"
            3 -> "取消"
            4 -> "申請"
            else -> "不正"
        }
    }
}