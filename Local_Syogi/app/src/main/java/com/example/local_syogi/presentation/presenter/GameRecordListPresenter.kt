package com.example.local_syogi.presentation.presenter

import com.example.local_syogi.presentation.contact.GameRecordListContact
import com.example.local_syogi.syogibase.data.game.GameLog
import com.example.local_syogi.syogibase.domain.SyogiLogicUseCase

class GameRecordListPresenter(private val view:GameRecordListContact.View, private val usecase: SyogiLogicUseCase):GameRecordListContact.Presenter{

    override fun getGameAll():MutableList<String>{
        return usecase.getGameAll()
    }

    override fun getGameByMode(mode:Int):MutableList<String>{
        return usecase.getGameByMode(mode)
    }

    override fun getRecordByTitle(title:String):MutableList<GameLog>{
        return usecase.getRecordByTitle(title)
    }
}