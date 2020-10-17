package com.example.local_syogi.domain.model

/**
 * 友達リストモデル
 * userName：アカウント名
 * status  :取得状態
 *
 * status
 * 1・・・承認待ち　　　取消 or 承認  userIdが1なら取消 2なら承認
 * 2・・・フォロワー　　
 */

class FollowModel(val userName: String, val userId: String, val status: Int)