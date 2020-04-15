package com.example.local_syogi.presentation.contact.game

import com.example.local_syogi.syogibase.data.game.GameLog
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel

interface GameViewRateContact {

    interface View {
        // 将棋盤描画
        fun drawBoard()
        // 先手の駒描画
        fun drawBlackPiece(name: String, i: Int, j: Int)
        // 後手の駒描画
        fun drawWhitePiece(name: String, i: Int, j: Int)
        // 先手の持ち駒描画
        fun drawHoldPieceBlack(nameJP: String, stock: Int, count: Int)
        // 後手の持ち駒描画
        fun drawHoldPieceWhite(nameJP: String, stock: Int, count: Int)
        // ヒント描画
        fun drawHint(i: Int, j: Int)
        // 成るか判断するダイアログ生成
        fun showDialog()
        // 詰んだことをActivityに知らせる
        fun gameEnd(turn: Int)
        // 効果音を鳴らす
        fun playbackEffect()
        // 指した手を送信する
        fun moveEmit(log: GameLog)
    }

    interface Presenter {
        // 初期画面セット
        fun setReplayView(gameDetail: GameDetailSetitngModel)
        // タッチイベントロジック
        fun onTouchEvent(x: Int, y: Int)
        // 描画ロジック
        fun drawView()
        // 成り
        fun evolutionPiece(bool: Boolean)
        // 対局ログを返す
        fun getLog(winner: Int): MutableList<GameLog>

        // 受信したての繁栄
        fun socketMove(oldX: Int, oldY: Int, newX: Int, newY: Int, evolution: Boolean)
        // ターン変更
        fun setTurn(turn: Int)
        // 対局開始時の手番を記憶
        fun setStartTurn(turn: Int)
    }
}