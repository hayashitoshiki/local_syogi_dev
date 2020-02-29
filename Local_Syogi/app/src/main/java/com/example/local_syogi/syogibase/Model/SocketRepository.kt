package com.example.local_syogi.syogibase.Model

import com.example.syogibase.Model.Data.GameLog

interface SocketRepository {
    //動かしたことを送信　駒を動かしたら送信
    fun moveEmit(gameLog: GameLog)
    //勝敗結果を送信　　投了ボタン。詰ませた。 判定モーダルが出たら送信
    fun gameEndEmit(turn:Int)

    interface presenter{
        //対局開始を受信　自動的 activityの変更
        fun socketStartGame(turn:Int)
        //駒の動きを受信。受信側は判定を行わない　　viewの変更
        fun socketMove(oldX:Int, oldY:Int, newX:Int, newY:Int)
        //勝敗結果を受信　activityの変更
        fun socketGameEnd(turn:Int)
    }

}