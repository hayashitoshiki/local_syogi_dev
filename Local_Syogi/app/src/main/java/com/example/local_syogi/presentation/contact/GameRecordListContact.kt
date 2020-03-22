package com.example.local_syogi.presentation.contact

import com.example.local_syogi.syogibase.data.game.GameLog
import com.example.local_syogi.syogibase.domain.model.GameModel

interface GameRecordListContact {
    interface View
    interface Presenter {
        // 棋譜を全権取得
        fun getGameAll(): MutableList<GameModel>
        // 指定したモードの対局一覧を取得
        fun getGameByMode(mode: Int): MutableList<GameModel>
        // 指定した対局の棋譜を取得
        fun getRecordByTitle(title: String): MutableList<GameLog>
    }
}