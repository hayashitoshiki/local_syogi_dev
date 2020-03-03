package com.example.local_syogi

import com.example.local_syogi.contact.GameViewRateContact
import com.example.local_syogi.presenter.GameLogicRatePresenter
import com.example.local_syogi.syogibase.Model.BoardRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class GameLogicRatePresenterTest {

    //モック
    private val repository = BoardRepository()
    private val repositoryMock = mock<BoardRepository>{
        on { getCountHoldPiece(1) } doReturn 1
        //on {getAllHoldPiece(GameLogicRatePresenter.BLACK)} donReturn
    }
    private val view = mock<GameViewRateContact.View>{}
    //テストクラス
    private val presenter:GameLogicRatePresenter = GameLogicRatePresenter(view,repositoryMock)

    //画面タッチ　自分の盤上の駒　盤上のヒント or 盤上のその他 or 持ち駒　or 盤外

    //ヒント取得 全種類の駒で全通りの行ける場所のテスト

    //駒を動かす

    //王手判定

    //将棋盤描画

    //成り判定

    //成る

    //詰み判定

    //持ち駒を使う



    //ターンチェンジ
    @Test
    fun setTurn(){
        presenter.socketMove(2,6,2,5)
        //verify(repository, times(1)).setPre(2,6)

    }

}