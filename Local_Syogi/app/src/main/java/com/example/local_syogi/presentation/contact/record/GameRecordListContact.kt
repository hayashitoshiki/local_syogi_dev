package com.example.local_syogi.presentation.contact.record

import com.example.local_syogi.syogibase.data.game.GameLog
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.syogibase.domain.model.GameModel

interface GameRecordListContact {
    interface View
    interface Presenter {
        // 棋譜を全権取得
        fun getGameAll(): MutableList<GameModel>
        // オンライン対戦の棋譜を全権取得
        fun getOnlineGameAll(): List<GameModel>
        // オフライン対戦の棋譜を全権取得
        fun getOfflineGameAll(): List<GameModel>
        // 指定したモードの対局一覧を取得
        fun getGameByMode(mode: Int): MutableList<GameModel>
        // オンライン対戦の指定したモードの対局一覧を取得
        fun getOnlineGameByMode(mode: Int): List<GameModel>
        // オフライン対戦の指定したモードの対局一覧を取得
        fun getOfflineGameByMode(mode: Int): List<GameModel>
        // 指定した対局の棋譜を取得
        fun getRecordByTitle(title: String): MutableList<GameLog>
        // 　指定した対局の詳細設定を取得
        fun getRecordSettingByTitle(title: String): GameDetailSetitngModel
    }
}