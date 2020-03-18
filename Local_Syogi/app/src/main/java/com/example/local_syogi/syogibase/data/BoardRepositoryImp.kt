package com.example.local_syogi.syogibase.data

import android.util.Log
import com.example.local_syogi.syogibase.data.local.Board
import com.example.local_syogi.syogibase.data.local.Cell
import com.example.local_syogi.syogibase.data.local.GameLog
import com.example.local_syogi.syogibase.data.local.GameMode
import com.example.local_syogi.syogibase.util.Piece
import com.example.local_syogi.syogibase.util.PieceMove


class BoardRepositoryImp:BoardRepository {
    private val board: Board = Board()
    private val logList = mutableListOf<GameLog>()

    private var previousX: Int = 0
    private var previousY: Int = 0
    private var previousPiece: Piece = Piece.None

    companion object {
        const val BLACK = 1
        const val WHITE = 2
    }


    //動かす前の駒の状態をセット
    override fun setPre(x: Int, y: Int) {
        previousX = x
        previousY = y
        Log.d("Main", "駒：" + ",x:" + previousX + ",y:" + previousY)
        Log.d("Main","駒セット。サイズ："+ logList.size)
        if (y == 10 || y == -1) previousPiece = changeIntToPiece(x)
        else previousPiece = board.cells[previousX][previousY].piece
        //Log.d("Main","駒："+ board.cells[x][y].piece + ",x:" + previousX +",y:" + previousY)
    }

    //最新手を返す
    override fun getLogList(): GameLog {
        return logList.last()
    }

    //対局ログを返す
    override fun getLog():MutableList<GameLog>{
        Log.d("Main","サイズ："+ logList.size)
        return logList
    }

    //駒を動かす
    override fun setMove(x: Int, y: Int, turn: Int,evolution:Boolean) {
        val piece = changeIntToPiece(previousX)
        val gameLog = GameLog(previousX, previousY, previousPiece, turn, x, y, board.cells[x][y].piece, board.cells[x][y].turn,evolution)
        logList.add(gameLog)
        board.cells[x][y].piece =
            if(evolution) previousPiece.evolution()
            else previousPiece
        board.cells[x][y].turn = turn
        when(previousY){
            10   -> board.holdPieceBlack[piece] = board.holdPieceBlack[piece]!! - 1
            -1   -> board.holdPieceWhite[piece] = board.holdPieceWhite[piece]!! - 1
            else -> {
                board.cells[previousX][previousY].piece = Piece.None
                board.cells[previousX][previousY].turn = 0
            }
        }
        Log.d("Main", "駒：" + board.cells[x][y].piece)
        Log.d("Main","サイズ："+ logList.size)
    }

    //１手戻す
    override fun setBackMove() {
        val log: GameLog = logList.last()
        when(log.oldY){
            10 -> board.holdPieceBlack[changeIntToPiece(log.oldX)] = board.holdPieceBlack[changeIntToPiece(log.oldX)]!! + 1
            -1 -> board.holdPieceWhite[changeIntToPiece(log.oldX)] = board.holdPieceWhite[changeIntToPiece(log.oldX)]!! + 1
            else ->{
                board.cells[log.oldX][log.oldY].piece = log.afterPiece
                board.cells[log.oldX][log.oldY].turn = log.afterTurn
            }
        }
        board.cells[log.newX][log.newY].piece = log.beforpiece
        board.cells[log.newX][log.newY].turn = log.beforturn
        logList.remove(log)
    }

    //成る
    override fun setEvolution() {
        val log: GameLog = logList.last()
        logList.last().evolution = true
        board.cells[log.newX][log.newY].piece = log.afterPiece.evolution()
    }

    //成れる駒か判別
    override fun findEvolutionBy(x: Int, y: Int): Boolean {
        return board.cells[x][y].piece.findEvolution()
    }

    //打った駒の打つ前のY軸取得
    override fun findLogY(): Int {
        return logList.last().oldY
    }

    //ヒントが表示されているマスの数を返す
    override fun getCountByHint(): Int {
        var count = 0
        board.cells.forEach { count += it.filter { it.hint }.count() }

        return count
    }

    //持ち駒リスト取得
    override fun getAllHoldPiece(turn: Int): Map<Piece, Int> {
        return if (turn == BLACK) {
            board.holdPieceBlack
        } else {
            board.holdPieceWhite
        }
    }

    //持ち駒の数を取得
    override fun getCountHoldPiece(turn: Int): Int {
        var count = 0
        for ((_, v) in getAllHoldPiece(turn)) {
            count += v
        }
        return count
    }

    //持ち駒マスから取得
    override fun findHoldPieceBy(i: Int, turn: Int): Piece {
        if (turn == 1 && board.holdPieceBlack[changeIntToPiece(i)] == 0 ||
            turn == 2 && board.holdPieceWhite[changeIntToPiece(i)] == 0
        ) return Piece.None

        return changeIntToPiece(i)
    }

    //持ち駒台の座標から駒を取得(本当はよくない)
    private fun changeIntToPiece(i: Int): Piece {
        return when (i) {
            2 -> Piece.FU
            3 -> Piece.KYO
            4 -> Piece.KEI
            5 -> Piece.GIN
            6 -> Piece.KIN
            7 -> Piece.KAKU
            8 -> Piece.HISYA
            else -> Piece.None
        }
    }

    //取った駒を表示
    override fun getTakePice(): Piece {
        return logList.last().beforpiece
    }

    //持ち駒追加
    override fun setHoldPiece() {
        val log: GameLog = logList.last()
        if (log.beforpiece != Piece.None) {
            if (log.beforturn == 2) {
                when (log.beforpiece) {
                    Piece.FU, Piece.TO -> board.holdPieceBlack[Piece.FU] =
                        board.holdPieceBlack[Piece.FU]!! + 1
                    Piece.KYO, Piece.N_KYO -> board.holdPieceBlack[Piece.KYO] =
                        board.holdPieceBlack[Piece.KYO]!! + 1
                    Piece.KEI, Piece.N_KEI -> board.holdPieceBlack[Piece.KEI] =
                        board.holdPieceBlack[Piece.KEI]!! + 1
                    Piece.GIN, Piece.N_GIN -> board.holdPieceBlack[Piece.GIN] =
                        board.holdPieceBlack[Piece.GIN]!! + 1
                    Piece.KIN -> board.holdPieceBlack[Piece.KIN] =
                        board.holdPieceBlack[Piece.KIN]!! + 1
                    Piece.HISYA, Piece.RYU -> board.holdPieceBlack[Piece.HISYA] =
                        board.holdPieceBlack[Piece.HISYA]!! + 1
                    Piece.KAKU, Piece.UMA -> board.holdPieceBlack[Piece.KAKU] =
                        board.holdPieceBlack[Piece.KAKU]!! + 1
                    else -> Log.e("BoaedRepository", "不正な駒を取ろうとしています")
                }
            } else if (log.beforturn == 1) {
                when (log.beforpiece) {
                    Piece.FU, Piece.TO -> board.holdPieceWhite[Piece.FU] =
                        board.holdPieceWhite[Piece.FU]!! + 1
                    Piece.KYO, Piece.N_KYO -> board.holdPieceWhite[Piece.KYO] =
                        board.holdPieceWhite[Piece.KYO]!! + 1
                    Piece.KEI, Piece.N_KEI -> board.holdPieceWhite[Piece.KEI] =
                        board.holdPieceWhite[Piece.KEI]!! + 1
                    Piece.GIN, Piece.N_GIN -> board.holdPieceWhite[Piece.GIN] =
                        board.holdPieceWhite[Piece.GIN]!! + 1
                    Piece.KIN -> board.holdPieceWhite[Piece.KIN] =
                        board.holdPieceWhite[Piece.KIN]!! + 1
                    Piece.HISYA, Piece.RYU -> board.holdPieceWhite[Piece.HISYA] =
                        board.holdPieceWhite[Piece.HISYA]!! + 1
                    Piece.KAKU, Piece.UMA -> board.holdPieceWhite[Piece.KAKU] =
                        board.holdPieceWhite[Piece.KAKU]!! + 1
                    else -> Log.e("BoaedRepository", "不正な駒を取ろうとしています")
                }
            }
        }
    }

    //強制的にならないといけない駒かチェック
    override fun checkForcedevolution(): Boolean {
        val log: GameLog = logList.last()
        val piece = board.cells[log.newX][log.newY].piece
        if ((piece == Piece.KYO && (((log.newY <= 1 && log.afterTurn == 1) || (7 <= log.newY && log.afterTurn == 2)))) ||
            (piece == Piece.KEI && (((log.newY <= 1 && log.afterTurn == 1) || (7 <= log.newY && log.afterTurn == 2)))) ||
            piece == Piece.FU || piece == Piece.HISYA || piece == Piece.KAKU
        ) return true
        return false
    }

    //駒の動きを取得
    override fun getMove(x: Int, y: Int): Array<Array<PieceMove>> {
        return getPiece(x, y).getMove()
    }

    //ヒントセット
    override fun setHint(x: Int, y: Int) {
        board.cells[x][y].hint = true
    }

    //ヒントリセット
    override fun resetHint() {
        board.cells.forEach { it.forEach { it.hint = false } }
    }

    //そのマスの駒の所有者を返す
    override fun getTurn(x: Int, y: Int): Int {
        return board.cells[x][y].turn
    }

    //そのマスのヒントを返す
    override fun getHint(x: Int, y: Int): Boolean {
        return board.cells[x][y].hint
    }

    //そのマスの駒を返す
    override fun getPiece(x: Int, y: Int): Piece {
        // TODO 安南だったら一段下の駒を取得
        return if (GameMode.getAnnanMode()) {
            if (board.cells[x][y].turn == BLACK && y + 1 <= 8 && getTurn(
                    x,
                    y + 1
                ) == board.cells[x][y].turn
            ) {
                board.cells[x][y + 1].piece
            } else if (board.cells[x][y].turn == WHITE && y - 1 >= 0 && getTurn(
                    x,
                    y - 1
                ) == board.cells[x][y].turn
            ) {
                board.cells[x][y - 1].piece
            } else {
                board.cells[x][y].piece
            }
        } else {
            board.cells[x][y].piece
        }
        //return board.cells[x][y].piece
    }

    //そのマスの駒の名前を返す
    override fun getJPName(x: Int, y: Int): String {
        return board.cells[x][y].piece.nameJP
    }

    //指定した手番の王様の座標を返す
    override fun findKing(turn: Int): Pair<Int, Int> {
        for (i in 0..8) for (j in 0..8) if (board.cells[i][j].piece == Piece.OU && board.cells[i][j].turn == turn) return Pair(
            i,
            j
        )
        return Pair(0, 0)
    }

    //指定したマスの情報を返す
    override fun getCellInformation(x: Int, y: Int): Cell {
        return board.cells[x][y]
    }

}