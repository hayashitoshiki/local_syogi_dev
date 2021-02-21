package com.example.local_syogi.presentation.contact.record

import com.example.local_syogi.syogibase.data.entity.game.GameLog
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.syogibase.domain.model.GameModel

interface GameRecordListContact {
    interface View
    interface Presenter {
        // オンライン対戦の棋譜を取得
        fun getOnlineGameList(mode: Int): List<GameModel>
        // オフライン対戦の棋譜を取得
        fun getOfflineGameList(mode: Int): List<GameModel>
        // 指定した対局の棋譜を取得
        fun getRecordByTitle(title: String): MutableList<GameLog>
        // 　指定した対局の詳細設定を取得
        fun getRecordSettingByTitle(title: String): GameDetailSetitngModel
    }
}
