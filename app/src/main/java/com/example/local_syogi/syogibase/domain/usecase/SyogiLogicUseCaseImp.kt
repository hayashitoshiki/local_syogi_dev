package com.example.local_syogi.syogibase.domain.usecase

import android.util.Log
import com.example.local_syogi.syogibase.data.entity.game.GameLog
import com.example.local_syogi.syogibase.data.entity.game.GameMode
import com.example.local_syogi.syogibase.data.repository.BoardRepository
import com.example.local_syogi.syogibase.data.repository.GameRecordRepository
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.syogibase.domain.model.GameModel
import com.example.local_syogi.syogibase.util.IntUtil.BLACK
import com.example.local_syogi.syogibase.util.IntUtil.WHITE
import com.example.local_syogi.syogibase.util.Piece
import com.example.local_syogi.syogibase.util.Piece.*
import com.example.local_syogi.syogibase.util.PieceMove

class SyogiLogicUseCaseImp(private val boardRepository: BoardRepository, private val recordRepository: GameRecordRepository) :
    SyogiLogicUseCase {

    private var turn: Int = BLACK
    private var secondTime = false

    private var positionList = mutableMapOf<String, Int>()

    // 駒落ち設定
    override fun setHandi(turn: Int, handi: Int) {
        boardRepository.setHandi(turn, handi)
    }

    // 駒を動かした後～王手判定
    override fun checkGameEnd(): Boolean {
        val turnOpponent = if (turn == BLACK) WHITE else BLACK
        val (kingX: Int, kingY: Int) = boardRepository.findKing(turnOpponent)

        // もし王手&詰み判定
        if (checkJudg(kingX, kingY, turnOpponent) && (GameMode.getCheckmateMode() || checkmate())) {
            return true
        }
        turn = if (turn == BLACK) WHITE else BLACK
        return false
    }

    // 指定したマスのヒント探す
    override fun setTouchHint(x: Int, y: Int) {
        boardRepository.resetHint()
        searchHint(x, y, turn)
    }

    // ヒント取得
    private fun searchHint(touchX: Int, touchY: Int, turn: Int) {
        val moveList: Array<Array<PieceMove>> = boardRepository.getMove(touchX, touchY)

        for (moveDirection in moveList) {
            for (move in moveDirection) {
                val (newX: Int, newY: Int) =
                    if (turn == BLACK) Pair(touchX + move.x, touchY + move.y)
                    else Pair(touchX - move.x, touchY - move.y)
                // 動かした先が盤上で自分の駒以外だったらヒントチェック
                if (newX in 0..8 && newY in 0..8 && boardRepository.getTurn(newX, newY) != turn) {
                    // TODO 持ち駒制限将棋
                    if (pieceLimitJudg(newX, newY)) break

                    setHint(touchX, touchY, newX, newY, turn)
                }
                if (newX in 0..8 && newY in 0..8 && boardRepository.getTurn(newX, newY) != 0) break
            }
        }
    }

    // ヒントを設定する
    override fun setHint(x: Int, y: Int, newX: Int, newY: Int, turn: Int) {
        boardRepository.setPre(x, y)
        boardRepository.setMove(newX, newY, turn, false)
        val (kingX: Int, kingY: Int) = boardRepository.findKing(turn)
        if (!checkJudg(kingX, kingY, turn)) boardRepository.setHint(newX, newY)
        boardRepository.setPreBackMove()
    }

    // 持ち駒制限将棋
    fun pieceLimitJudg(x: Int, y: Int): Boolean {
        if (GameMode.pieceLimit && boardRepository.getTurn(x, y) != 0 && boardRepository.getCountHoldPiece(turn) + 1 >= GameMode.pieceLimitCount)return true
        return false
    }

    // TODO 2手差し将棋ならここで王手判断、駒を取ったか判断
    override fun twohandRule() {
        if (GameMode.twoTimes && boardRepository.getTakePice() == None && !secondTime) {
            turn = if (turn == BLACK) WHITE else BLACK
            secondTime = true
        } else {
            secondTime = false
        }
    }

    // 駒を動かす
    override fun setMove(x: Int, y: Int, evolution: Boolean) {
        boardRepository.setMove(x, y, turn, evolution)
        boardRepository.setHoldPiece()
        boardRepository.resetHint()
        val board = boardRepository.getBoard()
        var position: String = ""
        board.forEach {
            it.forEach {
                position += it.hint.toString() + it.piece.toString() + it.turn.toString()
            }
        }
        if (positionList.containsKey(position)) {
            positionList[position] = positionList[position]!!.toInt() + 1
        } else {
            positionList[position] = 1
        }
    }

    // 成り判定
    override fun evolutionCheck(x: Int, y: Int): Boolean {
        val preY = boardRepository.findLogY()
        if ((preY in 0..8 && boardRepository.findEvolutionBy(x, y)) && ((turn == BLACK && (y <= 2 || preY <= 2)) || (turn == WHITE && (6 <= y || 6 <= preY)))) {
            return true
        }
        return false
    }

    // キャンセル
    override fun cancel() {
        boardRepository.resetHint()
    }

    // 王手判定
    private fun checkJudg(c: Int, r: Int, turnKing: Int): Boolean {
        // ↑
        for (j in 1..8) {
            if (0 <= r - j) {
                val cellTurn = boardRepository.getTurn(c, r - j)
                val cellPiece = boardRepository.getPiece(c, r - j)
                if (cellTurn == turnKing) break
                else if (j == 1 && ((cellPiece.equalUpMovePiece() && turnKing == BLACK) || (cellPiece.equalDownMovePiece() && turnKing == WHITE))) return true
                else if ((cellPiece == HISYA || cellPiece == RYU) || (cellPiece == KYO && cellTurn == BLACK && turnKing == WHITE)) return true
                else if (cellTurn != 0) break
            } else {
                // 盤外
                break
            }
        }
        // ↓
        for (j in 1..8) {
            if (r + j < 9) {
                val cellTurn = boardRepository.getTurn(c, r + j)
                val cellPiece = boardRepository.getPiece(c, r + j)
                if (cellTurn == turnKing) break
                else if (j == 1 && ((cellPiece.equalDownMovePiece() && turnKing == BLACK) || (cellPiece.equalUpMovePiece() && turnKing == WHITE))) return true
                else if ((cellPiece == HISYA || cellPiece == RYU) || (cellPiece == KYO && cellTurn == BLACK && turnKing == WHITE)) return true
                else if (cellTurn != 0) break
            } else {
                // 盤外
                break
            }
        }
        // ←
        for (j in 1..8) {
            if (0 <= c - j) {
                val cellTurn = boardRepository.getTurn(c - j, r)
                val cellPiece = boardRepository.getPiece(c - j, r)
                if (cellTurn == turnKing) break
                else if (j == 1 && cellPiece.equalLRMovePiece()) return true
                else if (cellPiece.equalLongLRMovePiece()) return true
                else if (cellTurn != 0) break
            } else {
                // 盤外
                break
            }
        }
        // →
        for (j in 1..8) {
            if (c + j < 9) {
                val cellTurn = boardRepository.getTurn(c + j, r)
                val cellPiece = boardRepository.getPiece(c + j, r)
                if (cellTurn == turnKing) break
                else if (cellPiece.equalLRMovePiece() && j == 1) return true
                else if (cellPiece.equalLongLRMovePiece()) return true
                else if (cellTurn != 0) break
            } else {
                // 盤外
                break
            }
        }
        // ↖
        for (j in 1..8) {
            if (0 <= c - j && 0 <= r - j) {
                val cellTurn = boardRepository.getTurn(c - j, r - j)
                val cellPiece = boardRepository.getPiece(c - j, r - j)
                if (cellTurn == turnKing) break
                else if (j == 1 && ((cellPiece.equalDiagonalUp() && turnKing == BLACK) || (cellPiece.equalDiagonalDown() && turnKing == WHITE))) return true
                else if (cellPiece == KAKU || cellPiece == UMA) return true
                else if (cellTurn != 0) break
            } else {
                // 盤外
                break
            }
        }
        // ↙
        for (j in 1..8) {
            if (0 <= c - j && r + j < 9) {
                val cellTurn = boardRepository.getTurn(c - j, r + j)
                val cellPiece = boardRepository.getPiece(c - j, r + j)
                if (cellTurn == turnKing) break
                else if (j == 1 && ((cellPiece.equalDiagonalDown() && turnKing == BLACK) || (cellPiece.equalDiagonalUp() && turnKing == WHITE))) return true
                else if (cellPiece == KAKU || cellPiece == UMA) return true
                else if (cellTurn != 0) break
            } else {
                // 盤外
                break
            }
        }
        // ↗
        for (j in 1..8) {
            if (c + j < 9 && 0 <= r - j) {
                val cellTurn = boardRepository.getTurn(c + j, r - j)
                val cellPiece = boardRepository.getPiece(c + j, r - j)
                if (cellTurn == turnKing) break
                else if (j == 1 && ((cellPiece.equalDiagonalUp() && turnKing == BLACK) || (cellPiece.equalDiagonalDown() && turnKing == WHITE))) return true
                else if (cellPiece == KAKU || cellPiece == UMA) return true
                else if (cellTurn != 0) break
            } else {
                // 盤外
                break
            }
        }
        // ↘
        for (j in 1..8) {
            if (c + j < 9 && r + j < 9) {
                val cellTurn = boardRepository.getTurn(c + j, r + j)
                val cellPiece = boardRepository.getPiece(c + j, r + j)
                if (cellTurn == turnKing) break
                else if (j == 1 && ((cellPiece.equalDiagonalDown() && turnKing == BLACK) || (cellPiece.equalDiagonalUp() && turnKing == WHITE))) return true
                else if (cellPiece == KAKU || cellPiece == UMA) return true
                else if (cellTurn != 0) break
            } else {
                // 盤外
                break
            }
        }

        // 桂馬のきき
        if (turn == BLACK && 0 <= r - 2) {
            if (0 <= c - 1 && boardRepository.getPiece(c - 1, r - 2) == KEI && boardRepository.getTurn(c - 1, r - 2) != turnKing) return true
            if (c + 1 < 9 && boardRepository.getPiece(c + 1, r - 2) == KEI && boardRepository.getTurn(c + 1, r - 2) != turnKing) return true
        } else if (r + 2 < 9) {
            if (0 <= c - 1 && boardRepository.getPiece(c - 1, r + 2) == KEI && boardRepository.getTurn(c - 1, r + 2) != turnKing) return true
            if (c + 1 < 9 && boardRepository.getPiece(c + 1, r + 2) == KEI && boardRepository.getTurn(c + 1, r + 2) != turnKing) return true
        }

        // 王手がなかったらfalseを返す
        return false
    }

    // 逃げる場所判定
    private fun checkmate(): Boolean {
        val turn = if (this.turn == BLACK) WHITE else BLACK

        // 逃げる場所 or 防げる駒があるか判定
        for (i in 0..8) for (j in 0..8) if (boardRepository.getTurn(i, j) == turn) {
            searchHint(i, j, turn)
        }

        // もしHint(逃げる場所)がなかったら詰み
        val count = boardRepository.getCountByHint()
        boardRepository.resetHint()
        if (count == 0) {
            return true
        }
        return false
    }

    // 成り判定 強制か否か
    override fun compulsionEvolutionCheck(): Boolean {
        if (boardRepository.checkForcedevolution()) {
            evolutionPiece(true)
            return true
        }
        return false
    }

    // 成り
    override fun evolutionPiece(bool: Boolean) {
        if (bool) boardRepository.setEvolution()
    }

    // 持ち駒を使う場合
    override fun setHintHoldPiece(x: Int, y: Int) {
        boardRepository.resetHint()
        val (newX, newY) = if (y == 10) Pair(x, y) else Pair(8 - x, -1)
        val piece =
            if (y == 0 && turn == WHITE) boardRepository.findHoldPieceBy(8 - x, turn)
            else if (y == 10 && turn == BLACK) boardRepository.findHoldPieceBy(x, turn)
            else None
        if (piece == None) return

        boardRepository.setPre(newX, newY)
        when (piece) {
            GIN, KIN, HISYA, KAKU ->
                for (i in 0..8) {
                    for (j in 0..8) {
                        if (boardRepository.getTurn(i, j) == 0) {
                            setHint(newX, newY, i, j, turn)
                        }
                    }
                }
            KYO ->
                for (i in 0..8) {
                    for (j in 1..8) {
                        val k = if (turn == BLACK) j else 8 - j
                        if (boardRepository.getTurn(i, k) == 0) {
                            setHint(newX, newY, i, k, turn)
                        }
                    }
                }
            KEI ->
                for (i in 0..8) {
                    for (j in 2..8) {
                        val k = if (turn == BLACK) j else 8 - j
                        if (boardRepository.getTurn(i, k) == 0) {
                            setHint(newX, newY, i, k, turn)
                        }
                    }
                }
            FU -> {
                val xList = mutableListOf<Int>()
                val yList = mutableListOf<Int>()
                for (i in 0..8) {
                    for (j in 0..8) {
                        if (boardRepository.getTurn(i, j) == turn && boardRepository.getPiece(i, j) == FU) break
                        if (j == 8) {
                            for (k in 1..8) {
                                val l = if (y == 10) k else k - 1
                                if (boardRepository.getTurn(i, l) == 0 && !isCheckMateByPossessionFu(newX, newY, i, l, turn)) {
                                    xList.add(i)
                                    yList.add(l)
                                   // setHint(newX, newY, i, K, turn)
                                }
                            }
                        }
                    }
                }
                xList.forEachIndexed { i, x ->
                    setHint(newX, newY, x, yList[i], turn)
                }
            }
            else -> Log.e("GameLogicPresenter", "不正な持ち駒を取得しようとしています")
        }
    }

    // 打ち歩詰め判定
    private fun isCheckMateByPossessionFu(x: Int, y: Int, newX: Int, newY: Int, turn: Int): Boolean {
        boardRepository.setPre(x, y)
        boardRepository.setMove(newX, newY, turn, false)
        if (checkmate()) {
            boardRepository.setPreBackMove()
            return true
        }
        boardRepository.setPreBackMove()
        return false
    }

    // (駒の名前,手番,ヒントの表示)を返す
    override fun getCellInformation(x: Int, y: Int): Triple<String, Int, Boolean> {
        val cell = boardRepository.getCellInformation(x, y)

        return Triple(cell.piece.nameJP, cell.turn, cell.hint)
    }

    // (駒の名前,手番,ヒントの表示)を返す
    override fun getCellTrun(x: Int, y: Int): Int {
        val cell = boardRepository.getCellInformation(x, y)
        return when {
            cell.hint -> 3
            cell.turn == BLACK -> BLACK
            cell.turn == WHITE -> WHITE
            else ->  4
        }
    }

    // 指定した手番の持ち駒を返す
    override fun getPieceHand(turn: Int): MutableList<Pair<Piece, Int>> {
        val hold = mutableListOf<Pair<Piece, Int>>()
        var i = 0
        boardRepository.getAllHoldPiece(turn).forEach { (piece, count) ->
            hold.add(i, Pair(piece, count))
            i++
        }
        return hold
    }

    // 現在の手番を返す
    override fun getTurn(): Int {
        return turn
    }

    // 手番を設定する
    override fun setTurn(turn: Int) {
        this.turn = turn
    }

    // 最後のログを取得する
    override fun getLogLast(): GameLog {
        return boardRepository.getLogList()
    }

    // 動かす駒の元の位置をセットする
    override fun setPre(x: Int, y: Int) {
        boardRepository.setPre(x, y)
    }

    // 対局ログを返す
    override fun getLog(): MutableList<GameLog> {
        return boardRepository.getLog()
    }

    // 一手戻す
    override fun setBackMove() {
        boardRepository.setBackMove()
    }

    // DBに保存
    override fun saveTable(log: MutableList<GameLog>, winner: Int, type: Int, blackName: String, whiteName: String, winType: Int) {
        recordRepository.save(log, winner, type, blackName, whiteName, winType)
    }

    // 全ての棋譜リストを取得する
    override fun getGameAll(): MutableList<GameModel> {
        val titlesList = recordRepository.findTitleByAll()
        val titleList = mutableListOf<GameModel>()
        titlesList.forEach {
            titleList.add(GameModel(it.title!!, it.winner!!, it.winType!!, it.type!!, it.turnCount!!, it.blackName!!, it.whiteName!!))
        }
        return titleList
    }

    // 特定の種類の棋譜リストを取得する
    override fun getGameByMode(mode: Int): MutableList<GameModel> {
        val titlesList = recordRepository.findTitleByMode(mode)
        val titleList = mutableListOf<GameModel>()
        titlesList.forEach {
            titleList.add(GameModel(it.title!!, it.winner!!, it.winType!!, it.type!!, it.turnCount!!, it.blackName!!, it.whiteName!!))
        }
        return titleList
    }

    // 指定した対局の棋譜を返す
    override fun getRecordByTitle(title: String): MutableList<GameLog> {
        val recordList = recordRepository.findRecordByTitle(title)
        val logList = mutableListOf<GameLog>()
        recordList.forEach {
            val log = GameLog(
                it.toX!! - 1,
                it.toY!! - 1,
                Piece.getByNameJP(it.toPiece!!),
                it.toTurn!!,
                it.fromX!! - 1,
                it.fromY!! - 1,
                Piece.getByNameJP(it.fromPiece!!),
                it.fromTurn!!,
                it.evolution!!)
            logList.add(log)
        }
        return logList
    }

    // 指定した対局の設定取得
    override fun getRecordSettingByTitle(title: String): GameDetailSetitngModel {
        val recordList = recordRepository.findDetaileByTitle(title)

        return GameDetailSetitngModel(recordList.handyBlack!!, recordList.handyWhite!!)
    }

    // 棋譜の手番変更
    override fun changeLogTurn(logList: MutableList<GameLog>): MutableList<GameLog> {
        val newLogList = mutableListOf<GameLog>()
        logList.forEach {
            val oldY = changeY(it.oldY)
            val oldX = changeX(it.oldX, oldY)
            val newY = changeY(it.newY)
            val newX = changeX(it.newX, newY)
            val log = GameLog(
                oldX,
                oldY,
                it.afterPiece,
                changeTurn(it.afterTurn),
                newX,
                newY,
                it.beforpiece,
                changeTurn(it.beforturn),
                it.evolution)
            newLogList.add(log)
        }
        return newLogList
    }

    // 持ち駒の場合と盤上の場合を加味してX軸の反転
    private fun changeX(x: Int, y: Int): Int {
        return when (y) {
            -1, 10 -> x
            else -> 8 - x
        }
    }
    private fun changeY(y: Int): Int {
        return when (y) {
            -1 -> 10
            10 -> -1
            else -> 8 - y
        }
    }

    // 手番変更
    private fun changeTurn(turn: Int): Int {
        return when (turn) {
            BLACK -> WHITE
            WHITE -> BLACK
            else -> 0
        }
    }

    // 千日手判定
    override fun isRepetitionMove(): Boolean {
        positionList.forEach { (_, v) ->
            if (v >= 4)return true
        }
        return false
    }

    // トライルール判定
    override fun isTryKing(): Boolean {
        val cell = when(turn) {
            BLACK -> boardRepository.getCellInformation(4, 0)
            WHITE -> boardRepository.getCellInformation(4, 8)
            else -> null
        }
        cell?.let {
            if ((cell.piece == GYOKU || cell.piece == OU) && cell.turn == turn) {
                return true
            }
        }

        return false
    }
}