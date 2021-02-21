package com.example.local_syogi.data.remote.dto

import com.example.local_syogi.data.entity.FollowEntity

class FollowDto(
    val code: Int,
    val message: String,
    val follow: ArrayList<FollowEntity>
)
