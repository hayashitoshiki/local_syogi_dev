package com.example.local_syogi.data.remote.dto

import com.example.local_syogi.data.entity.AccountEntity

class AccountDto(
    val code: Int,
    val message: String,
    val data: ArrayList<AccountEntity>
)
