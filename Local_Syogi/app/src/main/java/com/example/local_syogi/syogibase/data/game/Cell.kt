package com.example.local_syogi.syogibase.data.game

// １マスの情報クラス
import com.example.local_syogi.syogibase.util.Piece

class Cell {
    var piece: Piece
    var turn: Int
    var hint: Boolean

    init {
        piece = Piece.None
        turn = 0
        hint = false
    }
}