package com.example.local_syogi.presentation.presenter.game

import com.example.local_syogi.presentation.contact.game.GameViewRateContact
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.syogibase.domain.usecase.SyogiLogicUseCase

class GameLogicRatePresenter(private val view: GameViewRateContact.View, private val syogiUseCase: SyogiLogicUseCase) : GameViewRateContact.Presenter {

    companion object {
        const val BLACK = 1
        const val WHITE = 2
        const val HINT = 3
    }

    override fun setReplayView(gameDetail: GameDetailSetitngModel) {}

    // タッチ判定
    override fun onTouchEvent(x: Int, y: Int) {
        // 持ち駒
        if (y == 0 || y == 10)syogiUseCase.setHintHoldPiece(x, y)
        // 盤上
        else if (x in 0..8 && y in 1..9) {
            when (syogiUseCase.getCellTrun(x, y - 1)) {
                HINT -> {
                    syogiUseCase.setMove(x, y - 1, false)
                    if (syogiUseCase.evolutionCheck(x, y - 1) && !syogiUseCase.compulsionEvolutionCheck()) {
                        view.showDialog()
                    }
                    view.playbackEffect()
                    view.moveEmit(syogiUseCase.getLogLast())
                    if (syogiUseCase.checkGameEnd()) {
                        view.gameEnd(syogiUseCase.getTurn())
                    }
                    syogiUseCase.twohandRule()
                }
                syogiUseCase.getTurn() -> syogiUseCase.setTouchHint(x, y - 1)
                else -> syogiUseCase.cancel()
            }
        }
        // 盤外
        else syogiUseCase.cancel()
    }

    // 将棋描画
    override fun drawView() {
        view.drawBoard()
        // 盤上の駒セット
        for (i in 0..8) for (j in 0..8) {
            val(pieceName, turn, hint) = syogiUseCase.getCellInformation(i, j)
            when (turn) {
                BLACK -> view.drawBlackPiece(pieceName, i, j)
                WHITE -> view.drawWhitePiece(pieceName, i, j)
            }
            if (hint)view.drawHint(i, j)
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
    override fun evolutionPiece(bool: Boolean) {
        syogiUseCase.evolutionPiece(bool)
    }
    /**
     * 通信処理実装
     */
    // 動かす(通信、受信)
    override fun socketMove(oldX: Int, oldY: Int, newX: Int, newY: Int, evolution: Boolean) {
        syogiUseCase.setPre(oldX, oldY)
        syogiUseCase.setMove(newX, newY, evolution)
        syogiUseCase.setTurn(BLACK)
        view.playbackEffect()
    }

    override fun setTurn(turn: Int) {
        syogiUseCase.setTurn(turn)
    }
}