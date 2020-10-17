package com.example.local_syogi.data.remote.api

import com.example.local_syogi.data.entity.AccountEntity
import com.example.local_syogi.data.entity.FollowEntity
import com.example.local_syogi.data.remote.dto.AccountDto
import com.example.local_syogi.data.remote.dto.FollowDto
import com.example.local_syogi.data.remote.dto.StatusDto
import retrofit2.http.*

/**
 *  API一覧
 */

interface ApiService {

    // アカウント登録API
    @POST("/add/account")
    suspend fun createAccount(@Body account: AccountEntity): StatusDto

    // アカウント検索API
    @GET("/find/account.json")
    suspend fun findAccount(@Query("userId") userId: String): AccountDto

    // フォロー関係作成API
    @POST("/create/follow/")
    suspend fun createFollow(@Body follow: FollowEntity): StatusDto

    // フォロー中ユーザー取得API
    @GET("/find/follow.json")
    suspend fun findFollow(@Query("userId") userId: String): FollowDto

    // フォロー状態更新API
    @POST("/update/follow/")
    suspend fun updateFollow(@Body follow: FollowEntity): StatusDto

    // 　フォロー解除API
    @POST("/delete/follow")
    suspend fun deleteFollow(@Body follow: FollowEntity): StatusDto
}