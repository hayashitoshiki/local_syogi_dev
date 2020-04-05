package com.example.local_syogi.presentation.presenter.record

import com.example.local_syogi.presentation.contact.record.GameRecordListContact
import com.example.local_syogi.syogibase.data.game.GameLog
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.syogibase.domain.model.GameModel
import com.example.local_syogi.syogibase.domain.usecase.SyogiLogicUseCase

class GameRecordListPresenter(private val view: GameRecordListContact.View, private val usecase: SyogiLogicUseCase) : GameRecordListContact.Presenter {

    override fun getGameAll(): MutableList<GameModel> {
        return usecase.getGameAll()
    }

    override fun getGameByMode(mode: Int): MutableList<GameModel> {
        return usecase.getGameByMode(mode)
    }

    override fun getRecordByTitle(title: String): MutableList<GameLog> {
        return usecase.getRecordByTitle(title)
    }

    override fun getRecordSettingByTitle(title: String): GameDetailSetitngModel {
        return usecase.getRecordSettingByTitle(title)
    }
}