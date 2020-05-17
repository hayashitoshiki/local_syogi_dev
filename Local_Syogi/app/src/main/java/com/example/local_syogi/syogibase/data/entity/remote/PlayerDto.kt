package com.example.local_syogi.syogibase.data.entity.remote

/**
 * socket.ioに送るユーザー情報
 * id
 * name
 * gameMode
 *
 * gameMode
 *   const val NORMAL = 1
 *   const val BACKMOVE = 2
 *   const val CHECKMATE = 3
 *   const val LIMIT = 4
 *   const val TWOTIME = 5
 */
class PlayerDto(private val name: String, private val mode: Int)