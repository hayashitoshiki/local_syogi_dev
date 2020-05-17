package com.example.local_syogi.syogibase.data.entity.game

/**
 * マス目の情報クラス
 *   piece・・・駒の名前
 *   turn ・・・マス目の手番
 *   hint ・・・ヒント状態
 */
import com.example.local_syogi.syogibase.util.Piece

class Cell(
    var piece: Piece = Piece.None,
    var turn: Int = 0,
    var hint: Boolean = false
)