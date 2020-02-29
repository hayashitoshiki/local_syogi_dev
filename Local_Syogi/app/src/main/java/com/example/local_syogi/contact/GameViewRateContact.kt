package com.example.local_syogi.contact

import com.example.syogibase.Contact.GameViewContact

interface GameViewRateContact {

    interface View  {
        //将棋盤描画
        fun drawBoard()
        //先手の駒描画
        fun drawBlackPiece(name:String, i:Int, j:Int)
        //後手の駒描画
        fun drawWhitePiece(name:String, i:Int, j:Int)
        //先手の持ち駒描画
        fun drawHoldPieceBlack(nameJP:String, stock:Int, count:Int)
        //後手の持ち駒描画
        fun drawHoldPieceWhite(nameJP:String, stock:Int, count:Int)
        //ヒント描画
        fun drawHint(i:Int,j:Int)
        //成るか判断するダイアログ生成
        fun showDialog()
        //詰んだことをActivityに知らせる
        fun gameEnd(turn:Int)
        //効果音を鳴らす
        fun playbackEffect()
        fun reDraw()
        fun gameStart()
    }

    interface Presenter{
        //タッチイベントロジック
        fun onTouchEvent(x:Int,y:Int)
        //描画ロジック
        fun drawView()
        //成り
        fun evolutionPiece(bool:Boolean)

        fun gameEndEmit(turn:Int)
        fun activityDestroy()
    }
}