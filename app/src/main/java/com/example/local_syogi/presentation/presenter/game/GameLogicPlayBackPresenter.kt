package com.example.local_syogi.presentation.presenter.game

import com.example.local_syogi.presentation.contact.game.GameViewPlayBackContact
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.syogibase.domain.usecase.SyogiLogicUseCase
import com.example.local_syogi.syogibase.util.IntUtil.BLACK
import com.example.local_syogi.syogibase.util.IntUtil.WHITE

class GameLogicPlayBackPresenter(private val view: GameViewPlayBackContact.View, private val syogiUseCase: SyogiLogicUseCase) : GameViewPlayBackContact.Presenter {

    // 初期画面セット
    override fun setReplayView(gameDetail: GameDetailSetitngModel) {
        syogiUseCase.setHandi(BLACK, gameDetail.handyBlack)
        syogiUseCase.setHandi(WHITE, gameDetail.handyWite)
    }
    // タッチ判定
    override fun onTouchEvent(x: Int, y: Int) {}

    // 将棋描画
    override fun drawView() {
        view.drawBoard()
        // 盤上の駒セット
        for (i in 0..8) for (y in 0..8) {
            val x = 8 - i
            val(pieceName, turn, hint) = syogiUseCase.getCellInformation(i, y)
            when (turn) {
                BLACK -> view.drawBlackPiece(pieceName, x, y)
                WHITE -> view.drawWhitePiece(pieceName, x, y)
            }
        }
        // 順番に見て駒と枚数を返す 駒と枚数  表示するリストを作ってその添え字と返ってきた枚数。0以外なら
        syogiUseCase.getPieceHand(BLACK).forEachIndexed { index, piece ->
            if (piece.second != 0) {
                view.drawHoldPieceBlack(piece.first.nameJP, piece.second, index)
            }
        }
        syogiUseCase.getPieceHand(WHITE).forEachIndexed { index, piece ->
            if (piece.second != 0) {
                view.drawHoldPieceWhite(piece.first.nameJP, piece.second, index)
            }
        }
    }

    // 一手進む
    override fun setGoMove(oldX: Int, oldY: Int, newX: Int, newY: Int, evolution: Boolean) {
        syogiUseCase.setPre(oldX, oldY)
        syogiUseCase.setMove(newX, newY, evolution)
        changeTurn()
        view.playbackEffect()
    }

    // 一手戻る
    override fun setBackMove() {
        syogiUseCase.setBackMove()
        changeTurn()
    }

    // ターン変更
    private fun changeTurn() {
        when (syogiUseCase.getTurn()) {
            BLACK -> syogiUseCase.setTurn(WHITE)
            WHITE -> syogiUseCase.setTurn(BLACK)
        }
    }
}