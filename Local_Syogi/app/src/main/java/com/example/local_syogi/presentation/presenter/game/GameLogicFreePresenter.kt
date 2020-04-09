package com.example.local_syogi.presentation.presenter.game

import com.example.local_syogi.presentation.contact.game.GameViewRateContact
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.syogibase.domain.usecase.SyogiLogicUseCase

class GameLogicFreePresenter(private val view: GameViewRateContact.View, private val syogiUseCase: SyogiLogicUseCase) : GameViewRateContact.Presenter {

    companion object {
        const val BLACK = 1
        const val WHITE = 2
        const val HINT = 3
    }

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
            if (hint)view.drawHint(x, y)
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

    // 成り判定
    override fun evolutionPiece(bool: Boolean) {}

    // 駒の動きを受信。受信側は判定を行わない　　viewの変更
    override fun socketMove(oldX: Int, oldY: Int, newX: Int, newY: Int, evolution: Boolean) {
        syogiUseCase.setPre(oldX, oldY)
        syogiUseCase.setMove(newX, newY, evolution)
        changeTurn()
        view.playbackEffect()
    }

    // 一手戻す
    fun setBackMove() {
        syogiUseCase.setBackMove()
        changeTurn()
    }
    // ターン変更
    private fun changeTurn() {
        val turn = syogiUseCase.getTurn()
        if (turn == BLACK) {
            syogiUseCase.setTurn(WHITE)
        } else {
            syogiUseCase.setTurn(BLACK)
        }
    }
    // ターン変更
    override fun setTurn(turn: Int) {}
}