package com.example.local_syogi.syogibase.data.game

// 将棋盤定義クラス

import com.example.local_syogi.syogibase.util.IntUtil.BLACK
import com.example.local_syogi.syogibase.util.IntUtil.WHITE
import com.example.local_syogi.syogibase.util.Piece
import com.example.local_syogi.syogibase.util.Piece.*

class Board {

    // マスの数
    companion object {
        const val COLS = 9
        const val ROWS = 9
    }

    // ９×９の将棋盤
    val cells = Array(COLS, { Array(ROWS, { Cell() }) })

    // 持ち駒　名前　数
    var holdPieceBlack = mutableMapOf<Piece, Int>(
        FU to 0,
        KYO to 0,
        KEI to 0,
        GIN to 0,
        KIN to 0,
        KAKU to 0,
        HISYA to 0
    )

    var holdPieceWhite = mutableMapOf<Piece, Int>(
        FU to 0,
        KYO to 0,
        KEI to 0,
        GIN to 0,
        KIN to 0,
        KAKU to 0,
        HISYA to 0
    )

    // 将棋盤の生成
    init {
        // 初期の配置をセット
        for (i in 0..8) {
            // 歩
            cells[i][2].piece = FU
            cells[i][2].turn = 2
            cells[i][6].piece = FU
            cells[i][6].turn = 1
            if (i == 4) {
                // 王
                cells[i][0].piece = OU
                cells[i][0].turn = 2
                cells[i][8].piece = OU
                cells[i][8].turn = 1
            }
            if (i == 3 || i == 5) {
                // 金
                cells[i][0].piece = KIN
                cells[i][0].turn = 2
                cells[i][8].piece = KIN
                cells[i][8].turn = 1
            } else if (i == 2 || i == 6) {
                // 銀
                cells[i][0].piece = GIN
                cells[i][0].turn = 2
                cells[i][8].piece = GIN
                cells[i][8].turn = 1
            } else if (i == 1) {
                // 佳
                cells[i][0].piece = KEI
                cells[i][0].turn = 2
                cells[i][8].piece = KEI
                cells[i][8].turn = 1
                cells[i][7].piece = KAKU
                cells[i][7].turn = 1
                cells[i][1].piece = HISYA
                cells[i][1].turn = 2
            } else if (i == 7) {
                // 佳
                cells[i][0].piece = KEI
                cells[i][0].turn = 2
                cells[i][8].piece = KEI
                cells[i][8].turn = 1
                cells[i][7].piece = HISYA
                cells[i][7].turn = 1
                cells[i][1].piece = KAKU
                cells[i][1].turn = 2
            } else if (i == 0 || i == 8) {
                // 香
                cells[i][0].piece = KYO
                cells[i][0].turn = 2
                cells[i][8].piece = KYO
                cells[i][8].turn = 1
            }
        }
        // 安南
        if (GameMode.getAnnanMode()) {
            cells[1][2].piece = None
            cells[1][2].turn = 0
            cells[1][6].piece = None
            cells[1][6].turn = 0
            cells[1][2].piece = None
            cells[7][2].turn = 0
            cells[7][6].piece = None
            cells[7][6].turn = 0
            cells[7][2].piece = None
            cells[7][2].turn = 0
            cells[7][6].piece = None
            cells[7][6].turn = 0
            cells[1][3].piece = FU
            cells[1][3].turn = 2
            cells[1][5].piece = FU
            cells[1][5].turn = 1
            cells[1][3].piece = FU
            cells[7][3].turn = 2
            cells[7][5].piece = FU
            cells[7][5].turn = 1
            cells[7][3].piece = FU
            cells[7][3].turn = 2
            cells[7][5].piece = FU
            cells[7][5].turn = 1
        }
    }

    // 駒落ちセット
    fun setHandi(turn: Int, handi: Int) {
        when (turn) {
            BLACK -> {
                if (handi >= 8) {
                    cells[2][8].piece = None
                    cells[2][8].turn = 0
                    cells[6][8].piece = None
                    cells[6][8].turn = 0
                }
                if (handi >= 7) {
                    cells[1][8].piece = None
                    cells[1][8].turn = 0
                    cells[7][8].piece = None
                    cells[7][8].turn = 0
                }
                if (handi >= 6) {
                    cells[0][8].piece = None
                    cells[0][8].turn = 0
                    cells[8][8].piece = None
                    cells[8][8].turn = 0
                }
                if (handi >= 4) {
                    cells[7][7].piece = None
                    cells[7][7].turn = 0
                }
                if (handi == 3 || handi >= 5) {
                    cells[1][7].piece = None
                    cells[1][7].turn = 0
                }
            }
            WHITE -> {
                if (handi >= 8) {
                    cells[2][0].piece = None
                    cells[2][0].turn = 0
                    cells[6][0].piece = None
                    cells[6][0].turn = 0
                }
                if (handi >= 7) {
                    cells[1][0].piece = None
                    cells[1][0].turn = 0
                    cells[7][0].piece = None
                    cells[7][0].turn = 0
                }
                if (handi >= 6) {
                    cells[0][0].piece = None
                    cells[0][0].turn = 0
                    cells[8][0].piece = None
                    cells[8][0].turn = 0
                }
                if (handi >= 4) {
                    cells[1][1].piece = None
                    cells[1][1].turn = 0
                }
                if (handi == 3 || handi >= 5) {
                    cells[7][1].piece = None
                    cells[7][1].turn = 0
                }
            }
        }
    }
}