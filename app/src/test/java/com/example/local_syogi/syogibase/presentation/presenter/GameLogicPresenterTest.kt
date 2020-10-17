package com.example.local_syogi.syogibase.presentation.presenter

import com.example.local_syogi.syogibase.domain.usecase.SyogiLogicUseCase
import com.example.local_syogi.syogibase.presentation.contact.GameViewContact
import com.example.local_syogi.syogibase.util.Piece
import com.nhaarman.mockito_kotlin.*
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString

/**
 * 下記のテストを行う
 * タッチイベント
 * ・駒台
 * ・盤上
 * 　・自分の駒
 * 　・相手の駒
 * 　・ヒントが表示されているマス
 * 　　・成る選択肢があるマス
 * 　　・詰むマス
 * 　・その他のマス
 * ・その他
 * 将棋盤描画
 * 　※先手の駒,後手の駒,何もなし,駒＋ヒント,ヒント,駒台(駒あり,駒なし)
 */

class GameLogicPresenterTest {

    companion object {
        const val BLACK = 1
        const val WHITE = 2
        const val HINT = 3
        const val OTHER = 4
    }

    /**
     * 駒台をタップした場合
     * y== 0 or Y == 10
     * 正常系：usecase.cancel()が呼ばれる
     */
    @Test
    fun onTouchPieceStand() {
        // テストクラス作成
        val view = mock<GameViewContact.View> {}
        val usecase = mock<SyogiLogicUseCase> {}
        val presenter = GameLogicPresenter(view, usecase)

        // 実行
        presenter.onTouchEvent(0, 0)
        presenter.onTouchEvent(2, 10)
        verify(usecase, times(2)).setHintHoldPiece(anyInt(), anyInt())
    }

    /**
     * 盤上の自分の駒をタップした場合
     * 正常系：自分の駒の動けるマスを返す
     */
    @Test
    fun onTouchMyPieceInBoard() {
        // テストクラス作成
        val (x, y) = Pair(5, 5)
        val view = mock<GameViewContact.View> {}
        val useCase = mock<SyogiLogicUseCase> {
            on { getCellTrun(x, y - 1) } doReturn BLACK
            on { getTurn() } doReturn BLACK
        }
        val presenter = GameLogicPresenter(view, useCase)
        // 実行
        presenter.onTouchEvent(x, y)
        verify(useCase, times(1)).setTouchHint(5, 4)
    }

    /**
     * 盤上の相手の駒をタップした場合
     * 正常系：ヒントを消して何もしない
     */
    @Test
    fun onTouchOpponentPieceInBoard() {
        // テストクラス作成
        val (x, y) = Pair(5, 5)
        val view = mock<GameViewContact.View> {}
        val useCase = mock<SyogiLogicUseCase> {
            on { getCellTrun(x, y - 1) } doReturn WHITE
            on { getTurn() } doReturn BLACK
        }
        val presenter = GameLogicPresenter(view, useCase)
        // 実行
        presenter.onTouchEvent(x, y)
        verify(useCase, times(1)).cancel()
    }

    /**
     * 盤上のヒントをタップした場合
     * 正常系：駒を動かす
     */
    @Test
    fun onTouchHintInBoard() {
        // テストクラス作成
        val (x, y) = Pair(5, 5)
        val view = mock<GameViewContact.View> {}
        val useCase = mock<SyogiLogicUseCase> {
            on { getCellTrun(x, y - 1) } doReturn HINT
            on { getTurn() } doReturn BLACK
        }
        val presenter = GameLogicPresenter(view, useCase)
        // 実行
        presenter.onTouchEvent(x, y)
        verify(useCase, times(1)).setMove(x, y - 1, false)
        verify(view, times(1)).playbackEffect()
    }

    /**
     * 盤上のヒント（成る選択肢あり）をタップした場合
     * 正常系：成りダイアログが呼ばれる
     */
    @Test
    fun onTouchHintEvolutionPiece() {
        // テストクラス作成
        val (x, y) = Pair(5, 5)
        val view = mock<GameViewContact.View> {}
        val useCase = mock<SyogiLogicUseCase> {
            on { getCellTrun(x, y - 1) } doReturn HINT
            on { getTurn() } doReturn BLACK
            on { evolutionCheck(x, y - 1) } doReturn true
            on { compulsionEvolutionCheck() } doReturn false
        }
        val presenter = GameLogicPresenter(view, useCase)
        // 実行
        presenter.onTouchEvent(x, y)
        verify(useCase, times(1)).setMove(x, y - 1, false)
        verify(view, times(1)).playbackEffect()
        verify(view, times(1)).showDialog()
    }

    /**
     * 盤上のヒント(詰む場所)をタップした場合
     * 正常系：終了ダイアログ表示メソッドが呼ばれる
     */
    @Test
    fun onTouchHintGameEnd() {
        // テストクラス作成
        val (x, y) = Pair(5, 5)
        val view = mock<GameViewContact.View> {}
        val useCase = mock<SyogiLogicUseCase> {
            on { getCellTrun(x, y - 1) } doReturn HINT
            on { getTurn() } doReturn BLACK
            on { checkGameEnd() } doReturn true
        }
        val presenter = GameLogicPresenter(view, useCase)
        // 実行
        presenter.onTouchEvent(x, y)
        verify(useCase, times(1)).setMove(x, y - 1, false)
        verify(view, times(1)).playbackEffect()
       // verify(view, times(1)).gameEnd(BLACK)
    }

    /**
     * 盤上のその他のマスをタップした場合
     * 正常系：ヒントを消して何もしない
     */
    @Test
    fun onTouchOtherInBoard() {
        // テストクラス作成
        val (x, y) = Pair(5, 5)
        val view = mock<GameViewContact.View> {}
        val useCase = mock<SyogiLogicUseCase> {
            on { getCellTrun(x, y - 1) } doReturn OTHER
            on { getTurn() } doReturn BLACK
        }
        val presenter = GameLogicPresenter(view, useCase)
        // 実行
        presenter.onTouchEvent(x, y)
        verify(useCase, times(1)).cancel()
    }

    /**
     * 盤外 & 駒台外　をタップした場合
     * 正常系：ヒントを消して何もしない
     */
    @Test
    fun onTouchOther() {
        // テストクラス作成
        val view = mock<GameViewContact.View> {}
        val useCase = mock<SyogiLogicUseCase> {}
        val presenter = GameLogicPresenter(view, useCase)

        // 実行
        presenter.onTouchEvent(5, -1)
        presenter.onTouchEvent(5, 11)
        presenter.onTouchEvent(-1, 5)
        presenter.onTouchEvent(9, 5)
        verify(useCase, times(4)).cancel()
    }

    /**
     * 盤面描画
     * →盤面描画 && 盤上(相手の駒 自分の駒　ヒントのみ　相手の駒+ヒント　なし) 駒台(駒あり 駒なし)
     */
    @Test
    fun drawView() {
        // テストクラス作成
        val pieceHand = mutableListOf<Pair<Piece, Int>>(Pair(Piece.FU, 1), Pair(Piece.KYO, 0))
        val view = mock<GameViewContact.View> {}
        val useCase = mock<SyogiLogicUseCase> {
            on { getCellInformation(eq(0), anyInt()) } doReturn Triple("歩", BLACK, false)
            on { getCellInformation(eq(1), anyInt()) } doReturn Triple("歩", BLACK, true)
            on { getCellInformation(eq(2), anyInt()) } doReturn Triple("歩", WHITE, false)
            on { getCellInformation(eq(3), anyInt()) } doReturn Triple("歩", WHITE, true)
            on { getCellInformation(eq(4), anyInt()) } doReturn Triple("歩", OTHER, true)
            on { getCellInformation(eq(5), anyInt()) } doReturn Triple("歩", OTHER, false)
            on { getCellInformation(eq(6), anyInt()) } doReturn Triple("歩", OTHER, false)
            on { getCellInformation(eq(7), anyInt()) } doReturn Triple("歩", OTHER, false)
            on { getCellInformation(eq(8), anyInt()) } doReturn Triple("歩", OTHER, false)
            on { getPieceHand(BLACK) } doReturn pieceHand
            on { getPieceHand(WHITE) } doReturn pieceHand
        }
        val presenter = GameLogicPresenter(view, useCase)
        // 実行
        presenter.drawView()
        verify(view, times(1)).drawBoard()
        verify(view, times(18)).drawBlackPiece(anyString(), anyInt(), anyInt())
        verify(view, times(18)).drawWhitePiece(anyString(), anyInt(), anyInt())
        verify(view, times(27)).drawHint(anyInt(), anyInt())
        verify(view, times(1)).drawHoldPieceBlack(anyString(), anyInt(), anyInt())
        verify(view, times(1)).drawHoldPieceWhite(anyString(), anyInt(), anyInt())
    }

    /**
     * 成り判定へそのままパスできるか
     * 正常系：BooleanをそのままuseCaseへ送る
     */
    @Test
    fun evolutionPiece() {
        // テストクラス作成
        val view = mock<GameViewContact.View> {}
        val useCase = mock<SyogiLogicUseCase> {}
        val presenter = GameLogicPresenter(view, useCase)
        // 実行
        presenter.evolutionPiece(true)
        verify(useCase, times(1)).evolutionPiece(true)
    }
}