package com.example.local_syogi.syogibase.presentation.contact

import com.example.local_syogi.syogibase.data.game.GameLog

interface GameViewContact {

    interface View {
        // 将棋盤描画
        fun drawBoard()
        // 先手の駒描画
        fun drawBlackPiece(name: String, i: Int, j: Int)
        // 後手の駒描画
        fun drawWhitePiece(name: String, i: Int, j: Int)
        // ヒント描画
        fun drawHint(i: Int, j: Int)
        // 先手の持ち駒描画
        fun drawHoldPieceBlack(nameJP: String, stock: Int, count: Int)
        // 後手の持ち駒描画
        fun drawHoldPieceWhite(nameJP: String, stock: Int, count: Int)
        // 成るか判断するダイアログ生成
        fun showDialog()
        // 対局終了モーダル生成
        fun gameEnd(turn: Int, winType: Int)
        // 効果音を鳴らす
        fun playbackEffect()
        // 手番チェンジ(チェスクロ変更など)
        fun changeTurn(turn: Int)
    }

    interface Presenter {
        // 初期設定
        fun startGame()
        // タッチイベントロジック
        fun onTouchEvent(x: Int, y: Int)
        // 描画ロジック
        fun drawView()
        // 成り判定
        fun evolutionPiece(bool: Boolean)

        // 対局ログを返す
        fun getLog(winner: Int, winType: Int): MutableList<GameLog>
    }
}