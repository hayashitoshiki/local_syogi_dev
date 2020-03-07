package com.example.local_syogi

import com.example.local_syogi.contact.GameViewRateContact
import com.example.local_syogi.presenter.GameLogicRatePresenter
import com.example.local_syogi.syogibase.Model.BoardRepository
import com.example.local_syogi.syogibase.Model.BoardRepositoryImp
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class GameLogicRatePresenterTest {

    //モック
    private val repository = BoardRepositoryImp()
    private val repositoryMock = mock<BoardRepository>{
        on { getCountHoldPiece(1) } doReturn 1
        //on {getAllHoldPiece(GameLogicRatePresenter.BLACK)} donReturn
    }
    private val view = mock<GameViewRateContact.View>{}
    //テストクラス
    private val presenter:GameLogicRatePresenter = GameLogicRatePresenter(view,repositoryMock)

    //画面タッチ

    //将棋盤描画



    //ターンチェンジ
    @Test
    fun setTurn(){
        presenter.socketMove(2,6,2,5)
        //verify(repository, times(1)).setPre(2,6)

    }

}