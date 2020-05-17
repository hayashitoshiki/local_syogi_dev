package com.example.local_syogi.syogibase.data.entity.game

object GameMode {
    var checkmate = false
    var annan = false
    var pieceLimit = false
    var pieceLimitCount = 3
    var twoTimes = false

    const val NORMAL = 1
    const val BACKMOVE = 2
    const val CHECKMATE = 3
    const val LIMIT = 4
    const val TWOTIME = 5
    const val CHAOS = 100

    fun getCheckmateMode(): Boolean {
        return checkmate
    }

    fun getAnnanMode(): Boolean {
        return annan
    }

    fun reset() {
        checkmate = false
        annan = false
        pieceLimit = false
        pieceLimitCount = 3
        twoTimes = false
    }

    // 現在選択しているモードを返す(String型)
    fun getModeText(): String {
        var title = "本将棋"
        if (annan) {
            title = "安南将棋"
        }
        if (checkmate) {
            if (title != "") {
                return "カオス将棋"
            }
            title = "王手将棋"
        }
        if (pieceLimit) {
            if (title != "") {
                return "カオス将棋"
            }
            title = "持ち駒制限将棋"
        }
        if (twoTimes) {
            if (title != "") {
                return "カオス将棋"
            }
            title = "２手差し将棋"
        }
        return title
    }

    // 現在の選択しているモードを返す(Int型)
    fun getModeInt(): Int {
        var mode = NORMAL
        if (annan) {
            mode = BACKMOVE
        }
        if (checkmate) {
            if (mode != NORMAL) {
                return CHAOS
            }
            mode = CHECKMATE
        }
        if (pieceLimit) {
            if (mode != NORMAL) {
                return CHAOS
            }
            mode = LIMIT
        }
        if (twoTimes) {
            if (mode != NORMAL) {
                return CHAOS
            }
            mode = TWOTIME
        }
        return mode
    }
}