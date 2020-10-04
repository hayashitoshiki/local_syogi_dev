package com.example.local_syogi.presentation.presenter.record

import com.example.local_syogi.presentation.contact.record.GameRecordListContact
import com.example.local_syogi.syogibase.data.entity.game.GameLog
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.syogibase.domain.model.GameModel
import com.example.local_syogi.syogibase.domain.usecase.SyogiLogicUseCase
import com.example.local_syogi.syogibase.util.IntUtil.FREE
import com.example.local_syogi.syogibase.util.IntUtil.RATE

class GameRecordListPresenter(private val view: GameRecordListContact.View, private val usecase: SyogiLogicUseCase) : GameRecordListContact.Presenter {

    // オフライン対局の記譜取得
    override fun getOfflineGameList(mode: Int): List<GameModel> {
        return when (mode) {
            0 -> usecase.getGameAll().filter { it.type == FREE }
            else -> usecase.getGameByMode(mode).filter { it.type == FREE }
        }
    }
    // オンライン対局の記譜取得
    override fun getOnlineGameList(mode: Int): List<GameModel> {
        return when (mode) {
            0 -> usecase.getGameAll().filter { it.type == RATE }
            else -> usecase.getGameByMode(mode).filter { it.type == RATE }
        }
    }

    // 指定のタイトルの対局取得
    override fun getRecordByTitle(title: String): MutableList<GameLog> {
        return usecase.getRecordByTitle(title)
    }

    // 指定のタイトルのハンデ取得
    override fun getRecordSettingByTitle(title: String): GameDetailSetitngModel {
        return usecase.getRecordSettingByTitle(title)
    }
}