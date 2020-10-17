package com.example.local_syogi.syogibase.domain.usecase

import com.example.local_syogi.syogibase.data.entity.game.GameLog
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.syogibase.domain.model.GameModel
import com.example.local_syogi.syogibase.util.Piece

interface SyogiLogicUseCase {

    /**
     * ベースのロジック：Base
     */
    // 駒落ち設定
    fun setHandi(turn: Int, handi: Int)
    // ヒントセットする
    fun setTouchHint(x: Int, y: Int)
    // 持ち駒を使う場合
    fun setHintHoldPiece(x: Int, y: Int)
    // 駒を動かす
    fun setMove(x: Int, y: Int, evolution: Boolean)
    // 成り判定
    fun evolutionCheck(x: Int, y: Int): Boolean
    // 成り判定 強制か否か
    fun compulsionEvolutionCheck(): Boolean
    // 成り
    fun evolutionPiece(bool: Boolean)
    // 駒を動かした後～王手判定
    fun checkGameEnd(): Boolean
    // キャンセル
    fun cancel()
    // (駒の名前,手番,ヒントの表示)を返す
    fun getCellInformation(x: Int, y: Int): Triple<String, Int, Boolean>
    // (駒の名前,手番,ヒントの表示)を返す
    fun getCellTrun(x: Int, y: Int): Int
    // 持ち駒を加工して返す
    fun getPieceHand(turn: Int): MutableList<Pair<Piece, Int>>
    // ターンを返す
    fun getTurn(): Int
    // 手番を設定する
    fun setTurn(turn: Int)
    // ヒントを設定する
    fun setHint(x: Int, y: Int, newX: Int, newY: Int, turn: Int)
    // 最後のログを取得する
    fun getLogLast(): GameLog
    // 動かす駒の元の位置をセットする
    fun setPre(x: Int, y: Int)

    /**
     * 将棋ルール：Rule
     */
    // TODO 2手差し将棋ならここで王手判断、駒を取ったか判断
    fun twohandRule()
    // 千日手判定
    fun isRepetitionMove(): Boolean
    // トライルール判定
    fun isTryKing(): Boolean

    /**
     *　保存系：Save
     */
    // 対局ログを返す
    fun getLog(): MutableList<GameLog>
    // 一手戻す
    fun setBackMove()
    // DBに保存
    fun saveTable(log: MutableList<GameLog>, winner: Int, type: Int, blackName: String, whiteName: String, winType: Int)
    // 全ての棋譜リストを取得する
    fun getGameAll(): MutableList<GameModel>
    // 特定の種類の棋譜リストを取得する
    fun getGameByMode(mode: Int): MutableList<GameModel>
    // 指定した対局の棋譜を返す
    fun getRecordByTitle(title: String): MutableList<GameLog>
    // 指定した対局の設定取得
    fun getRecordSettingByTitle(title: String): GameDetailSetitngModel
    // 通信対戦でもし後手を引いたらlogファイルの手番変更
    fun changeLogTurn(logList: MutableList<GameLog>): MutableList<GameLog>
}