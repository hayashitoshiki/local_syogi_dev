package com.example.local_syogi.syogibase.domain


import com.example.syogibase.Model.Data.Piece

interface SyogiLogicUseCase {

    //TODO 2手差し将棋ならここで王手判断、駒を取ったか判断
    fun twohandRule()
    //ヒントセットする
    fun setTouchHint(x:Int,y:Int)
    //持ち駒を使う場合
    fun setHintHoldPiece(x: Int, y: Int)
    //駒を動かす
    fun setMove(x: Int, y: Int)
    //成り判定
    fun evolutionCheck(x:Int, y:Int):Boolean
    //成り判定 強制か否か
    fun compulsionEvolutionCheck():Boolean
    //成り
    fun evolutionPiece(bool: Boolean)
    //駒を動かした後～王手判定
    fun checkGameEnd():Boolean
    //キャンセル
    fun cancel()
    //(駒の名前,手番,ヒントの表示)を返す
    fun getCellInformation(x:Int,y:Int):Triple<String,Int,Boolean>
    //(駒の名前,手番,ヒントの表示)を返す
    fun getCellTrun(x:Int,y:Int):Int
    //持ち駒を加工して返す
    fun getPieceHand(turn:Int):MutableList<Pair<Piece,Int>>
    //ターンを返す
    fun getTurn():Int
}
