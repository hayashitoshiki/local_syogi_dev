package com.example.local_syogi.syogibase.data.entity.remote

import com.example.local_syogi.syogibase.util.Piece

class MoveDto(
    val toX: Int,
    val toY: Int,
    val toPiece: Piece,
    val toTurn: Int,
    val fromX: Int,
    val fromY: Int,
    val fromPiece: Piece,
    val fromTurn: Int,
    val evolution: Boolean,
    val countNumber: Long
)