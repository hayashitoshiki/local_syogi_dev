package com.example.local_syogi.syogibase.presentation.presenter

import com.example.local_syogi.syogibase.data.game.GameLog
import com.example.local_syogi.syogibase.domain.SyogiLogicUseCase
import com.example.local_syogi.syogibase.presentation.contact.GameViewContact

class GameLogicPresenter(private val view: GameViewContact.View, private val usecase: SyogiLogicUseCase) : GameViewContact.Presenter {

    companion object {
        const val BLACK = 1
        const val WHITE = 2
        const val HINT = 3
    }

    // タッチ判定
    override fun onTouchEvent(x: Int, y: Int) {
        // 持ち駒
        if (y == 0 || y == 10)usecase.setHintHoldPiece(x, y)
        // 盤上
        else if (x in 0..8 && y in 1..9) {
            when (usecase.getCellTrun(x, y - 1)) {
                HINT -> {
                    usecase.setMove(x, y - 1, false)
                    if (usecase.evolutionCheck(x, y - 1) && !usecase.compulsionEvolutionCheck()) {
                        view.showDialog()
                    }
                    view.playbackEffect()
                    // 千日手判定
                    if (usecase.isRepetitionMove()) view.gameEnd(3)
                    // トライルール判定
                    if (usecase.isTryKing()) view.gameEnd(usecase.getTurn())
                    // 王手判定
                    if (usecase.checkGameEnd()) view.gameEnd(usecase.getTurn())
                    usecase.twohandRule()
                }
                usecase.getTurn() -> usecase.setTouchHint(x, y - 1)
                else -> usecase.cancel()
            }
        }
        // 盤外
        else usecase.cancel()
    }

    // 将棋描画
    override fun drawView() {
        view.drawBoard()
        // 盤上の駒セット
        for (i in 0..8) for (j in 0..8) {
            val(pieceName, turn, hint) = usecase.getCellInformation(i, j)
            when (turn) {
                BLACK -> view.drawBlackPiece(pieceName, i, j)
                WHITE -> view.drawWhitePiece(pieceName, i, j)
            }
            if (hint)view.drawHint(i, j)
        }
        // 順番に見て駒と枚数を返す 駒と枚数  表示するリストを作ってその添え字と返ってきた枚数。0以外なら
        usecase.getPieceHand(BLACK).forEachIndexed { index, piece ->
            if (piece.second != 0) {
                view.drawHoldPieceBlack(piece.first.nameJP, piece.second, index)
            }
        }
        usecase.getPieceHand(WHITE).forEachIndexed { index, piece ->
            if (piece.second != 0) {
                view.drawHoldPieceWhite(piece.first.nameJP, piece.second, index)
            }
        }
    }

    // 成り判定
    override fun evolutionPiece(bool: Boolean) {
        usecase.evolutionPiece(bool)
    }

    // 対局ログを返す
    override fun getLog(winner: Int): MutableList<GameLog> {
        val log = usecase.getLog()
        usecase.saveTable(log, winner)
        return log
    }
}