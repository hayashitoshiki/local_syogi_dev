package com.example.local_syogi.presentation.contact.game

import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel

interface GameViewPlayBackContact {

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
        // 効果音を鳴らす
        fun playbackEffect()
    }

    interface Presenter {
        // 初期画面セット
        fun setReplayView(gameDetail: GameDetailSetitngModel)
        // タッチイベントロジック
        fun onTouchEvent(x: Int, y: Int)
        // 描画ロジック
        fun drawView()
        // 一手進む
        fun setGoMove(oldX: Int, oldY: Int, newX: Int, newY: Int, evolution: Boolean)
        // 一手戻る
        fun setBackMove()
    }
}
