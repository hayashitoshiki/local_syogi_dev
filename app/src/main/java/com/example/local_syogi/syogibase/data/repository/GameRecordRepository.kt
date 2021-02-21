package com.example.local_syogi.syogibase.data.repository

import com.example.local_syogi.syogibase.data.entity.game.GameLog
import com.example.local_syogi.syogibase.data.entity.local.GameEntity
import com.example.local_syogi.syogibase.data.entity.local.RecordEntity

interface GameRecordRepository {

    // 新規対局データ格納
    fun save(logList: MutableList<GameLog>, winner: Int, type: Int, blackName: String, whiteName: String, winType: Int)
    // 全てのモードのタイトルを取得
    fun findTitleByAll(): Array<GameEntity>
    // 特定のモードのタイトルを取得
    fun findTitleByMode(mode: Int): Array<GameEntity>
    // 特定の対局データ取得
    fun findRecordByTitle(title: String): Array<RecordEntity>
    // 特定のタイトルの詳細を取得
    fun findDetaileByTitle(title: String): GameEntity
}
