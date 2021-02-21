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

class SyogiLogicUseCaseImp(
    private val boardRepository: BoardRepository,
    private val recordRepository: GameRecordRepository
) :
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
        if (isCheck(kingX, kingY, turnOpponent) && (GameMode.getCheckmateMode() || isCheckmate())) {
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
                var newX = 0
                var newY = 0
                when (turn) {
                    BLACK -> {
                        newX = touchX + move.x
                        newY = touchY + move.y
                    }
                    WHITE -> {
                        newX = touchX - move.x
                        newY = touchY - move.y
                    }
                }
                // 範囲外か自分のコマとかぶったらその方向の検索はストップ
                if (newX !in 0..8 || newY !in 0..8 || boardRepository.getTurn(newX, newY) == turn) {
                    break
                }
                // TODO 持ち駒制限将棋
                if (pieceLimitJudg(newX, newY)) {
                    break
                }

                setHint(touchX, touchY, newX, newY, turn)
                if (boardRepository.getTurn(newX, newY) != 0) break
            }
        }
    }

    // ヒントを設定する
    override fun setHint(x: Int, y: Int, newX: Int, newY: Int, turn: Int) {
        boardRepository.setPre(x, y)
        boardRepository.setMove(newX, newY, turn, false)
        val (kingX: Int, kingY: Int) = boardRepository.findKing(turn)
        if (!isCheck(kingX, kingY, turn)) boardRepository.setHint(newX, newY)
        boardRepository.setPreBackMove()
    }

    // 持ち駒制限将棋
    fun pieceLimitJudg(x: Int, y: Int): Boolean {
        return GameMode.pieceLimit && boardRepository.getTurn(
            x,
            y
        ) != 0 && boardRepository.getCountHoldPiece(turn) + 1 >= GameMode.pieceLimitCount
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
        var position = ""
        boardRepository.setMove(x, y, turn, evolution)
        boardRepository.setHoldPiece()
        boardRepository.resetHint()
        boardRepository.getBoard().forEach {
            it.forEach { cell ->
                position += cell.hint.toString() + cell.piece.toString() + cell.turn.toString()
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
        return (preY in 0..8 && boardRepository.findEvolutionBy(
            x,
            y
        )) && ((turn == BLACK && (y <= 2 || preY <= 2)) || (turn == WHITE && (6 <= y || 6 <= preY)))
    }

    // キャンセル
    override fun cancel() {
        boardRepository.resetHint()
    }

    // 王手判定
    private fun isCheck(x: Int, y: Int, turnKing: Int): Boolean {
        // ↑
        for (j in 1..8) {
            val moveY = y - j
            if (moveY < 0) break

            // 判定
            val cellTurn = boardRepository.getTurn(x, moveY)
            val cellPiece = boardRepository.getPiece(x, moveY)
            if (cellTurn == turnKing) break
            else if (j == 1 && ((cellPiece.equalUpMovePiece() && turnKing == BLACK) || (cellPiece.equalDownMovePiece() && turnKing == WHITE))) return true
            else if ((cellPiece == HISYA || cellPiece == RYU) || (cellPiece == KYO && cellTurn == BLACK && turnKing == WHITE)) return true
            else if (cellTurn != 0) break
        }
        // ↓
        for (j in 1..8) {
            val moveY = y + j
            if (9 <= moveY) break

            // 判定
            val cellTurn = boardRepository.getTurn(x, moveY)
            val cellPiece = boardRepository.getPiece(x, moveY)
            if (cellTurn == turnKing) break
            else if (j == 1 && ((cellPiece.equalDownMovePiece() && turnKing == BLACK) || (cellPiece.equalUpMovePiece() && turnKing == WHITE))) return true
            else if ((cellPiece == HISYA || cellPiece == RYU) || (cellPiece == KYO && cellTurn == BLACK && turnKing == WHITE)) return true
            else if (cellTurn != 0) break
        }
        // ←
        for (j in 1..8) {
            val moveX = x - j
            if (moveX < 0) break

            // 判定
            val cellTurn = boardRepository.getTurn(moveX, y)
            val cellPiece = boardRepository.getPiece(moveX, y)
            if (cellTurn == turnKing) break
            else if (j == 1 && cellPiece.equalLRMovePiece()) return true
            else if (cellPiece.equalLongLRMovePiece()) return true
            else if (cellTurn != 0) break
        }
        // →
        for (j in 1..8) {
            val moveX = x + j
            if (9 <= moveX) break

            // 判定
            val cellTurn = boardRepository.getTurn(moveX, y)
            val cellPiece = boardRepository.getPiece(moveX, y)
            if (cellTurn == turnKing) break
            else if (j == 1 && cellPiece.equalLRMovePiece()) return true
            else if (cellPiece.equalLongLRMovePiece()) return true
            else if (cellTurn != 0) break
        }
        // ↖
        for (j in 1..8) {
            val moveX = x - j
            val moveY = y - j
            if (moveX < 0 || moveY < 0) break

            // 判定
            val cellTurn = boardRepository.getTurn(moveX, moveY)
            val cellPiece = boardRepository.getPiece(moveX, moveY)
            if (cellTurn == turnKing) break
            else if (j == 1 && ((cellPiece.equalDiagonalUp() && turnKing == BLACK) || (cellPiece.equalDiagonalDown() && turnKing == WHITE))) return true
            else if (cellPiece == KAKU || cellPiece == UMA) return true
            else if (cellTurn != 0) break

        }
        // ↙
        for (j in 1..8) {
            val moveX = x - j
            val moveY = y + j
            if (moveX < 0 && 9 <= moveY) break

            // 判定
            val cellTurn = boardRepository.getTurn(moveX, moveY)
            val cellPiece = boardRepository.getPiece(moveX, moveY)
            if (cellTurn == turnKing) break
            else if (j == 1 && ((cellPiece.equalDiagonalDown() && turnKing == BLACK) || (cellPiece.equalDiagonalUp() && turnKing == WHITE))) return true
            else if (cellPiece == KAKU || cellPiece == UMA) return true
            else if (cellTurn != 0) break
        }
        // ↗
        for (j in 1..8) {
            val moveX = x + j
            val moveY = y - j
            if (9 <= moveX && moveY < 0) break

            // 判定
            val cellTurn = boardRepository.getTurn(moveX, moveY)
            val cellPiece = boardRepository.getPiece(moveX, moveY)
            if (cellTurn == turnKing) break
            else if (j == 1 && ((cellPiece.equalDiagonalUp() && turnKing == BLACK) || (cellPiece.equalDiagonalDown() && turnKing == WHITE))) return true
            else if (cellPiece == KAKU || cellPiece == UMA) return true
            else if (cellTurn != 0) break
        }
        // ↘
        for (j in 1..8) {
            val moveX = x + j
            val moveY = y + j
            if (9 <= moveX && 9 <= moveY) break

            // 判定
            val cellTurn = boardRepository.getTurn(moveX, moveY)
            val cellPiece = boardRepository.getPiece(moveX, moveY)
            if (cellTurn == turnKing) break
            else if (j == 1 && ((cellPiece.equalDiagonalDown() && turnKing == BLACK) || (cellPiece.equalDiagonalUp() && turnKing == WHITE))) return true
            else if (cellPiece == KAKU || cellPiece == UMA) return true
            else if (cellTurn != 0) break
        }

        // 桂馬のきき
        val y1 = y - 2
        val y2 = y + 2
        val x1 = x - 1
        val x2 = x + 1
        if (turn == BLACK && 0 <= y1) {
            if (0 <= x1 && boardRepository.getPiece(x1, y1) == KEI && boardRepository.getTurn(
                    x1,
                    y1
                ) != turnKing
            ) return true
            if (x2 < 9 && boardRepository.getPiece(x2, y1) == KEI && boardRepository.getTurn(
                    x1,
                    y1
                ) != turnKing
            ) return true
        } else if (turn == WHITE && y2 < 9) {
            if (0 <= x1 && boardRepository.getPiece(x1, y2) == KEI && boardRepository.getTurn(
                    x1,
                    y2
                ) != turnKing
            ) return true
            if (x2 < 9 && boardRepository.getPiece(x2, y2) == KEI && boardRepository.getTurn(
                    x2,
                    y2
                ) != turnKing
            ) return true
        }

        // 王手がなかったらfalseを返す
        return false
    }

    // 逃げる場所判定
    private fun isCheckmate(): Boolean {
        val turn = if (this.turn == BLACK) WHITE else BLACK

        // 逃げる場所 or 防げる駒があるか判定
        for (i in 0..8) {
            for (j in 0..8) {
                if (boardRepository.getTurn(i, j) == turn) {
                    searchHint(i, j, turn)
                }
            }
        }
        // TODO : 持ち駒使う
        getPieceHand(turn).forEachIndexed { index, piece ->
            if (piece.second != 0) {
                val (x, y) =
                    if (turn == BLACK) {
                        Pair(index + 2, 10)
                    } else {
                        Pair(8 - (index + 2), 0)
                    }
                setHintHoldPiece(x, y)
            }
        }

        // もしHint(逃げる場所)がなかったら詰み
        val count = boardRepository.getCountByHint()
        boardRepository.resetHint()
        return count == 0
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
            if ((y == 0 && turn == WHITE) || (y == 10 && turn == BLACK)) {
                boardRepository.findHoldPieceBy(newX, turn)
            } else {
                None
            }
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
                        if (boardRepository.getTurn(i, j) == turn && boardRepository.getPiece(
                                i,
                                j
                            ) == FU
                        ) break
                        if (j == 8) {
                            for (k in 1..8) {
                                val l = if (y == 10) k else k - 1
                                if (boardRepository.getTurn(
                                        i,
                                        l
                                    ) == 0 && !isCheckMateByPossessionFu(newX, newY, i, l, turn)
                                ) {
                                    xList.add(i)
                                    yList.add(l)
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
    private fun isCheckMateByPossessionFu(
        x: Int,
        y: Int,
        newX: Int,
        newY: Int,
        turn: Int
    ): Boolean {
        boardRepository.setPre(x, y)
        boardRepository.setMove(newX, newY, turn, false)
        val result = isCheckmate()
        boardRepository.setPreBackMove()
        return result
    }

    // (駒の名前,手番,ヒントの表示)を返す
    override fun getCellInformation(x: Int, y: Int): Triple<String, Int, Boolean> {
        val cell = boardRepository.getCellInformation(x, y)

        return Triple(cell.piece.nameJP, cell.turn, cell.hint)
    }

    // マスの手番を返す
    override fun getCellTrun(x: Int, y: Int): Int {
        val cell = boardRepository.getCellInformation(x, y)
        return when {
            cell.hint -> 3
            cell.turn == BLACK -> BLACK
            cell.turn == WHITE -> WHITE
            else -> 4
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
    override fun saveTable(
        log: MutableList<GameLog>,
        winner: Int,
        type: Int,
        blackName: String,
        whiteName: String,
        winType: Int
    ) {
        recordRepository.save(log, winner, type, blackName, whiteName, winType)
    }

    // 全ての棋譜リストを取得する
    override fun getGameAll(): MutableList<GameModel> {
        val titlesList = recordRepository.findTitleByAll()
        val titleList = mutableListOf<GameModel>()
        titlesList.forEach {
            titleList.add(
                GameModel(
                    it.title!!,
                    it.winner!!,
                    it.winType!!,
                    it.type!!,
                    it.turnCount!!,
                    it.blackName!!,
                    it.whiteName!!
                )
            )
        }
        return titleList
    }

    // 特定の種類の棋譜リストを取得する
    override fun getGameByMode(mode: Int): MutableList<GameModel> {
        val titlesList = recordRepository.findTitleByMode(mode)
        val titleList = mutableListOf<GameModel>()
        titlesList.forEach {
            titleList.add(
                GameModel(
                    it.title!!,
                    it.winner!!,
                    it.winType!!,
                    it.type!!,
                    it.turnCount!!,
                    it.blackName!!,
                    it.whiteName!!
                )
            )
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
                it.evolution!!
            )
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
                it.evolution
            )
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
            if (v >= 4) return true
        }
        return false
    }

    // トライルール判定
    override fun isTryKing(): Boolean {
        val cell = when (turn) {
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
