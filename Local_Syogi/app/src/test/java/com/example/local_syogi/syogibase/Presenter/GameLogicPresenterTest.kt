package com.example.local_syogi.syogibase.Presenter

import com.example.local_syogi.syogibase.Model.BoardRepository
import com.example.local_syogi.syogibase.domain.SyogiLogicUsecase
import com.example.syogibase.Contact.GameViewContact
import com.example.syogibase.Presenter.GameLogicPresenter
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test

import org.junit.Assert.*

/**
 * 下記のテストを行う
 * タッチイベント
 * ・駒台
 * ・盤上
 * 　・自分の駒
 * 　・相手の駒
 * 　・ヒントが表示されているマス
 * 　・その他のマス
 * ・その他
 * 将棋盤描画
 */

class GameLogicPresenterTest {

    //タッチイベント(駒台 or 盤上の自分の駒 盤上の相手の駒 盤上のヒント その他 or その他)
    @Test
    fun onTouchEvent() {
    }

    /**
     * 駒台をタップした場合
     * y== 0 or Y == 10
     * 正常系：
     */
    @Test
    fun onTouchPieceStand(){

    }

    /**
     * 盤上の自分の駒
     * 正常系：自分の駒の動けるマスを返す
     */
    @Test
    fun onTouchMyPieceInBoard(){

    }

    /**
     * 盤上の相手の駒
     * 正常系：ヒントを消して何もしない
     */
    @Test
    fun onTouchOpponentPieceInBoard(){

    }

    /**
     * 盤上のヒント
     * 正常系：ヒントを表示
     */
    @Test
    fun onTouchHintInBoard(){

    }

    /**
     * 盤上のその他
     * ヒントを消して何もしない
     */
    @Test
    fun onTouchOtherInBoard(){

    }

    /**
     * その他
     * ヒントを消して何もしない
     */
    @Test
    fun onTouchOther(){

    }


    //盤面描画
    @Test
    fun drawView() {
    }

    @Test
    fun evolutionPiece() {
    }
}