package com.example.local_syogi.presentation.presenter.game

import com.example.local_syogi.presentation.contact.game.GameViewRateContact
import com.example.local_syogi.syogibase.data.game.GameLog
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.syogibase.domain.usecase.SyogiLogicUseCase

class GameLogicRatePresenter(private val view: GameViewRateContact.View, private val syogiUseCase: SyogiLogicUseCase) : GameViewRateContact.Presenter {

    companion object {
        const val BLACK = 1
        const val WHITE = 2
        const val HINT = 3
    }
    private var startTurn: Int = 1

    override fun setReplayView(gameDetail: GameDetailSetitngModel) {}

    // タッチ判定
    override fun onTouchEvent(x: Int, y: Int) {
        // 相手の手番なら何もしない
        if (syogiUseCase.getTurn() == WHITE) {
            return
        }
        // 持ち駒
        if (y == 0 || y == 10)syogiUseCase.setHintHoldPiece(x, y)
        // 盤上
        else if (x in 0..8 && y in 1..9) {
            // domainの盤上型に修正
            val x2 = 8 - x
            val y2 = y - 1
            when (syogiUseCase.getCellTrun(x2, y2)) {
                HINT -> {
                    syogiUseCase.setMove(x2, y2, false)
                    if (syogiUseCase.evolutionCheck(x2, y2) && !syogiUseCase.compulsionEvolutionCheck()) {
                        view.showDialog()
                    }
                    view.playbackEffect()
                    view.moveEmit(syogiUseCase.getLogLast())
                    if (syogiUseCase.checkGameEnd()) {
                        view.gameEnd(syogiUseCase.getTurn())
                    }
                    syogiUseCase.twohandRule()
                }
                syogiUseCase.getTurn() -> syogiUseCase.setTouchHint(x2, y2)
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

    // 手番変更
    override fun setTurn(turn: Int) {
        syogiUseCase.setTurn(turn)
    }

    // 対局開始時の手番を記憶
    override fun setStartTurn(turn: Int) {
        startTurn = turn
    }

    // 対局ログを返す
    override fun getLog(winner: Int): MutableList<GameLog> {
        var log = syogiUseCase.getLog()
        if (startTurn == WHITE) {
            log = syogiUseCase.changeLogTurn(log)
        }
        syogiUseCase.saveTable(log, winner, 2)
        return log
    }
}