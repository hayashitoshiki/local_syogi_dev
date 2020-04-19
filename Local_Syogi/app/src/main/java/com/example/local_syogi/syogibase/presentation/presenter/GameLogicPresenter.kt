package com.example.local_syogi.syogibase.presentation.presenter

import com.example.local_syogi.di.MyApplication
import com.example.local_syogi.syogibase.data.game.GameLog
import com.example.local_syogi.syogibase.domain.usecase.SyogiLogicUseCase
import com.example.local_syogi.syogibase.presentation.contact.GameViewContact
import com.example.local_syogi.syogibase.presentation.view.GameSettingSharedPreferences
import com.example.local_syogi.syogibase.util.IntUtil.BLACK
import com.example.local_syogi.syogibase.util.IntUtil.HINT
import com.example.local_syogi.syogibase.util.IntUtil.WHITE

class GameLogicPresenter(private val view: GameViewContact.View, private val usecase: SyogiLogicUseCase) : GameViewContact.Presenter {

    private val sharedPreferences = GameSettingSharedPreferences(MyApplication.getInstance().applicationContext)

    // 初期設定
    override fun startGame() {
        // ハンデ設定
        usecase.setHandi(BLACK, sharedPreferences.getHandyBlack())
        usecase.setHandi(WHITE, sharedPreferences.getHandyWhite())
    }
    // タッチ判定
    override fun onTouchEvent(x: Int, y: Int) {
        // 持ち駒
        if (y == 0 || y == 10)usecase.setHintHoldPiece(x, y)
        // 盤上
        else if (x in 0..8 && y in 1..9) {
            // domainの盤上型に修正
            val x2 = 8 - x
            val y2 = y - 1
            when (usecase.getCellTrun(x2, y2)) {
                HINT -> {
                    usecase.setMove(x2, y2, false)
                    if (usecase.evolutionCheck(x2, y2) && !usecase.compulsionEvolutionCheck()) {
                        view.showDialog()
                    }
                    view.playbackEffect()
                    view.changeTurn(usecase.getTurn())
                    // 千日手判定
                    if (usecase.isRepetitionMove()) view.gameEnd(3, 0)
                    // トライルール判定
                    if (sharedPreferences.getTryRule() && usecase.isTryKing()) view.gameEnd(usecase.getTurn(), 1)
                    // 王手判定
                    if (usecase.checkGameEnd()) view.gameEnd(usecase.getTurn(), 1)
                    usecase.twohandRule()
                }
                usecase.getTurn() -> usecase.setTouchHint(x2, y2)
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
        for (i in 0..8) for (y in 0..8) {
            val x = 8 - i
            val(pieceName, turn, hint) = usecase.getCellInformation(i, y)
            when (turn) {
                BLACK -> view.drawBlackPiece(pieceName, x, y)
                WHITE -> view.drawWhitePiece(pieceName, x, y)
            }
            if (hint)view.drawHint(x, y)
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
    override fun getLog(winner: Int, winType: Int): MutableList<GameLog> {
        val log = usecase.getLog()
        usecase.saveTable(log, winner, 1, "先手", "後手", winType)
        return log
    }
}