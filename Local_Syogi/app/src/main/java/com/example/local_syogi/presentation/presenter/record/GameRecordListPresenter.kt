package com.example.local_syogi.presentation.presenter.record

import com.example.local_syogi.presentation.contact.record.GameRecordListContact
import com.example.local_syogi.syogibase.data.entity.game.GameLog
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.syogibase.domain.model.GameModel
import com.example.local_syogi.syogibase.domain.usecase.SyogiLogicUseCase

class GameRecordListPresenter(private val view: GameRecordListContact.View, private val usecase: SyogiLogicUseCase) : GameRecordListContact.Presenter {

    // 全部の対戦モードの対局取得
    override fun getGameAll(): MutableList<GameModel> {
        return usecase.getGameAll()
    }
    override fun getOfflineGameAll(): List<GameModel> {
        return usecase.getGameAll().filter { it.type == 1 }
    }
    override fun getOnlineGameAll(): List<GameModel> {
        return usecase.getGameAll().filter { it.type == 2 }
    }

    // 指定の対戦モードの対局取得
    override fun getGameByMode(mode: Int): MutableList<GameModel> {
        return usecase.getGameByMode(mode)
    }
    override fun getOfflineGameByMode(mode: Int): List<GameModel> {
        return usecase.getGameByMode(mode).filter { it.type == 1 }
    }
    override fun getOnlineGameByMode(mode: Int): List<GameModel> {
        return usecase.getGameByMode(mode).filter { it.type == 2 }
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