package com.example.local_syogi.syogibase.domain


import android.util.Log
import com.example.local_syogi.syogibase.Model.Data.GameMode
import com.example.local_syogi.syogibase.Model.BoardRepository
import com.example.syogibase.Model.Data.Piece
import com.example.syogibase.Model.Data.Piece.*
import com.example.syogibase.Model.Data.PieceMove

class SyogiLogicUsecase(private val boardRepository:BoardRepository) {

    companion object {
        const val BLACK = 1
        const val WHITE = 2
    }

    private var turn: Int = 1
    private var secondTime = false


    //駒を動かした後～王手判定
    fun checkGameEnd():Boolean{
        val turnOpponent = if (turn == BLACK) WHITE else BLACK
        val (kingX: Int, kingY: Int) = boardRepository.findKing(turnOpponent)

        //もし王手されていて、王手将棋じゃなかったら詰み判定を行い、王手将棋なら終了
        if (checkJudg(kingX, kingY, turnOpponent)) {
            //TODO 王手将棋ならここでゲーム終了
            if (GameMode.getCheckmateMode()) {
                return true
            } else if (checkmate()) {
               return true
            }
        }
        turn = if (turn == BLACK) WHITE else BLACK

        return false
    }

    //TODO 2手差し将棋ならここで王手判断、駒を取ったか判断
    fun twohandRule(){
        if (GameMode.twoTimes) {
            if (boardRepository.getTakePice() == None && !secondTime) {
                turn = if (turn == BLACK) WHITE else BLACK
                secondTime = true
            } else {
                secondTime = false
            }
        }
    }

    //ヒントセットする
    fun setHint(x:Int,y:Int){
        boardRepository.resetHint()
        getHint(x, y, turn)
    }

    //ヒント取得
    private fun getHint(touchX:Int, touchY:Int,turn:Int){

        val moveList:Array<Array<PieceMove>> = boardRepository.getMove(touchX, touchY)

        for(moveDirection in moveList){
            for(move in moveDirection){
                val (newX:Int, newY:Int) =
                    if(turn == BLACK) Pair(touchX+move.x, touchY+move.y)
                    else              Pair(touchX-move.x, touchY-move.y)
                //駒を動かしたとき自分の王様が王手にならなかったらヒントを表示
                if(newX in 0..8 && newY in 0..8 && boardRepository.getTurn(newX,newY) != turn) {
                    // TODO 持ち駒制限将棋
                    if(GameMode.pieceLimit && boardRepository.getTurn(newX,newY) != 0 && boardRepository.getCountHoldPiece(turn) + 1 >= GameMode.pieceLimitCount ){
                        break
                    }
                    boardRepository.setPre(touchX, touchY)
                    boardRepository.setMove(newX, newY, turn)
                    val (kingX: Int, kingY: Int) = boardRepository.findKing(turn)
                    if (!checkJudg(kingX, kingY, turn)) boardRepository.setHint(newX, newY)
                    boardRepository.setBackMove()
                }
                if(newX in 0..8 && newY in 0..8 && boardRepository.getTurn(newX,newY) != 0)break
            }
        }
        boardRepository.getCountByHint()
    }

    //駒を動かす
    fun setMove(x: Int, y: Int) {
        boardRepository.setMove(x, y, turn)
        boardRepository.setHoldPiece()
        boardRepository.resetHint()
    }

    //成り判定
    fun evolutionCheck(x:Int, y:Int):Boolean{
        val preY = boardRepository.findLogY()
        if ((preY in 0..8 && boardRepository.findEvolutionBy(x, y)) && ((turn == BLACK && (y <= 2 || preY <= 2)) || (turn == WHITE && (6 <= y || 6 <= preY)))){
            return true
        }
        return false
    }

    //キャンセル
    fun cancel() {
        boardRepository.resetHint()
    }

    //王手判定
    private fun checkJudg(c: Int, r: Int, turnKing: Int): Boolean {
        //↑
        for (j in 1..8) {
            if (0 <= r - j) {
                if (j == 1 && boardRepository.getTurn(c, r - j) != turnKing &&
                    ((boardRepository.findUpMovePiece(c, r - j) && turnKing == BLACK) ||
                            (boardRepository.findDownMovePiece(c, r - j) && turnKing == WHITE))
                ) return true
                else if (((boardRepository.getPiece(c, r - j) == HISYA ||
                            boardRepository.getPiece(c, r - j) == RYU) && boardRepository.getTurn(c, r - j) != turnKing) ||
                    (boardRepository.getPiece(c, r - j) == KYO && boardRepository.getTurn(c, r - j) == WHITE && turnKing == BLACK)
                ) return true
                else if (boardRepository.getTurn(c, r - j) != 0) break
            } else {
                //盤外
                break
            }
        }
        //↓
        for (j in 1..8) {
            if (r + j < 9) {
                if (j == 1 && boardRepository.getTurn(c, r + j) != turnKing &&
                    ((boardRepository.findDownMovePiece(c, r + j) && turnKing == BLACK) ||
                            (boardRepository.findUpMovePiece(c, r + j) && turnKing == WHITE))
                ) return true
                else if (((boardRepository.getPiece(c, r + j
                    ) == HISYA || boardRepository.getPiece(c, r + j
                    ) == RYU) && boardRepository.getTurn(c, r + j) != turnKing) ||
                    (boardRepository.getPiece(c, r + j) == KYO && boardRepository.getTurn(c, r + j
                    ) == BLACK && turnKing == WHITE)
                ) return true
                else if (boardRepository.getTurn(c, r + j) != 0) break
            } else {
                //盤外
                break
            }
        }
        //←
        for (j in 1..8) {
            if (0 <= c - j) {
                if (j == 1 && boardRepository.getTurn(c - j, r) != turnKing &&
                    boardRepository.findLRMovePiece(c - j, r)
                ) return true
                else if ((boardRepository.getPiece(c - j, r
                    ) == HISYA || boardRepository.getPiece(c - j, r
                    ) == RYU) && boardRepository.getTurn(c - j, r) != turnKing
                ) return true
                else if (boardRepository.getTurn(c - j, r) != 0) break
            } else {
                //盤外
                break
            }
        }
        //→
        for (j in 1..8) {
            if (c + j < 9) {
                if (boardRepository.getTurn(c + j, r) == turnKing) break
                else if (j == 1 && boardRepository.getTurn(c + j, r) != turnKing &&
                    boardRepository.findLRMovePiece(c + j, r)
                ) return true
                else if ((boardRepository.getPiece(c + j, r
                    ) == HISYA || boardRepository.getPiece(c + j, r
                    ) == RYU) && boardRepository.getTurn(c + j, r) != turnKing
                ) return true
                else if (boardRepository.getTurn(c + j, r) != 0) break
            } else {
                //盤外
                break
            }
        }
        //↖
        for (j in 1..8) {
            if (0 <= c - j && 0 <= r - j) {
                if (boardRepository.getTurn(c - j, r - j) == turnKing) break
                else if (j == 1 && boardRepository.getTurn(c - j, r - j) != turnKing &&
                    ((boardRepository.findDiagonalUp(c - j, r - j) && turnKing == BLACK) ||
                            (boardRepository.findDiagonalDown(c - j, r - j) && turnKing == WHITE))
                ) return true
                else if ((boardRepository.getPiece(c - j, r - j
                    ) == KAKU || boardRepository.getPiece(c - j, r - j
                    ) == UMA) && boardRepository.getTurn(c - j, r - j) != turnKing
                ) return true
                else if (boardRepository.getTurn(c - j, r - j) != 0) break
            } else {
                //盤外
                break
            }
        }
        //↙
        for (j in 1..8) {
            if (0 <= c - j && r + j < 9) {
                if (j == 1 && boardRepository.getTurn(c - j, r + j) != turnKing &&
                    ((boardRepository.findDiagonalDown(c - j, r + j) && turnKing == BLACK) ||
                            (boardRepository.findDiagonalUp(c - j, r + j) && turnKing == WHITE))
                ) return true
                else if ((boardRepository.getPiece(c - j, r + j
                    ) == KAKU || boardRepository.getPiece(c - j, r + j
                    ) == UMA) && boardRepository.getTurn(c - j, r + j) != turnKing
                ) return true
                else if (boardRepository.getTurn(c - j, r + j) != 0) break
            } else {
                //盤外
                break
            }
        }
        //↗
        for (j in 1..8) {
            if (c + j < 9 && 0 <= r - j) {
                if (j == 1 && boardRepository.getTurn(c + j, r - j) != turnKing &&
                    ((boardRepository.findDiagonalUp(c + j, r - j) && turnKing == BLACK) ||
                            (boardRepository.findDiagonalDown(c + j, r - j) && turnKing == WHITE))
                ) return true
                else if ((boardRepository.getPiece(c + j, r - j
                    ) == KAKU || boardRepository.getPiece(c + j, r - j
                    ) == UMA) && boardRepository.getTurn(c + j, r - j) != turnKing
                ) return true
                else if (boardRepository.getTurn(c + j, r - j) != 0) break
            } else {
                //盤外
                break
            }
        }
        //↘
        for (j in 1..8) {
            if (c + j < 9 && r + j < 9) {
                if (j == 1 && boardRepository.getTurn(c + j, r + j) != turnKing && ((boardRepository.findDiagonalDown(c + j, r + j) && turnKing == BLACK) || (boardRepository.findDiagonalUp(c + j, r + j) && turnKing == WHITE))) return true
                else if ((boardRepository.getPiece(c + j, r + j) == KAKU || boardRepository.getPiece(c + j, r + j) == UMA) && boardRepository.getTurn(c + j, r + j) != turnKing) return true
                else if (boardRepository.getTurn(c + j, r + j) != 0) break
            } else {
                //盤外
                break
            }
        }

        //桂馬のきき
        if (turn == BLACK && 0 <= r - 2) {
            val cell = boardRepository.getCellInformation(c-1,r-2)
            if (0 <= c - 1 && boardRepository.getPiece(c - 1, r - 2) == KEI && boardRepository.getTurn(c - 1, r - 2) != turnKing) return true
            if (c + 1 < 9 && boardRepository.getPiece(c + 1, r - 2) == KEI && boardRepository.getTurn(c + 1, r - 2) != turnKing) return true
        } else if (r + 2 < 9) {
            if (0 <= c - 1 && boardRepository.getPiece(c - 1, r + 2) == KEI && boardRepository.getTurn(c - 1, r + 2) != turnKing) return true
            if (c + 1 < 9 && boardRepository.getPiece(c + 1, r + 2) == KEI && boardRepository.getTurn(c + 1, r + 2) != turnKing) return true
        }

        //王手がなかったらfalseを返す
        return false
    }



    //逃げる場所判定
    private fun checkmate(): Boolean {
        val turn = if (this.turn == BLACK) WHITE else BLACK

        //逃げる場所 or 防げる駒があるか判定
        for (i in 0..8) for (j in 0..8) if (boardRepository.getTurn(i, j) == turn) {
            getHint(i, j, turn)
        }

        //もしHint(逃げる場所)がなかったら詰み
        val count = boardRepository.getCountByHint()
        boardRepository.resetHint()
        if (count == 0) return true
        return false
    }

    //成り判定 強制か否か
    fun compulsionEvolutionCheck():Boolean {
        if (boardRepository.checkForcedevolution()){
            evolutionPiece(true)
            return true
        }
        return false
    }

    //成り
    fun evolutionPiece(bool: Boolean) {
        if (bool) boardRepository.setEvolution()
    }

    //持ち駒を使う場合

    fun getHintHoldPiece(x: Int, y: Int) {
        val (newX, newY) = if (y == 10) Pair(x, y) else Pair(8 - x, -1)
        val piece =
            if (y == 0 && turn == WHITE) boardRepository.findHoldPieceBy(8 - x, turn)
            else if (y == 10 && turn == BLACK) boardRepository.findHoldPieceBy(x, turn)
            else None
        boardRepository.resetHint()
        if (piece == None) return

        boardRepository.setPre(newX, newY)
        when (piece) {
            GIN, KIN, HISYA, KAKU ->
                for (i in 0..8) {
                    for (j in 0..8) {
                        if (boardRepository.getTurn(i, j) == 0) {
                            boardRepository.setPre(newX, newY)
                            boardRepository.setMove(i, j, turn)
                            val (kingX: Int, kingY: Int) = boardRepository.findKing(turn)
                            if (!checkJudg(kingX, kingY, turn)) boardRepository.setHint(i, j)
                            boardRepository.setBackMove()
                        }
                    }
                }
            KYO ->
                for (i in 0..8) {
                    for (j in 1..8) {
                        val J = if (turn == BLACK) j else 8 - j
                        if (boardRepository.getTurn(i, J) == 0) {
                            boardRepository.setPre(newX, newY)
                            boardRepository.setMove(i, J, turn)
                            val (kingX: Int, kingY: Int) = boardRepository.findKing(turn)
                            if (!checkJudg(kingX, kingY, turn)) boardRepository.setHint(i, J)
                            boardRepository.setBackMove()
                        }
                    }
                }
            KEI ->
                for (i in 0..8) {
                    for (j in 2..8) {
                        val J = if (turn == BLACK) j else 8 - j
                        if (boardRepository.getTurn(i, J) == 0) {
                            boardRepository.setPre(newX, newY)
                            boardRepository.setMove(i, J, turn)
                            val (kingX: Int, kingY: Int) = boardRepository.findKing(turn)
                            if (!checkJudg(kingX, kingY, turn)) boardRepository.setHint(i, J)
                            boardRepository.setBackMove()
                        }
                    }
                }
            FU ->
                for (i in 0..8) {
                    for (j in 0..8) {
                        if (boardRepository.getTurn(i, j) == turn && boardRepository.getPiece(i, j) == FU) break
                        if (j == 8) {
                            for (k in 1..8) {
                                val K = if (y == 10) k else k - 1
                                if (boardRepository.getTurn(i, K) == 0) {
                                    boardRepository.setPre(newX, newY)
                                    boardRepository.setMove(i, K, turn)
                                    val (kingX: Int, kingY: Int) = boardRepository.findKing(turn)
                                    if (!checkJudg(kingX, kingY, turn)) boardRepository.setHint(i, K)
                                    boardRepository.setBackMove()
                                }
                            }
                        }
                    }
                }
            else -> Log.e("GameLogicPresenter", "不正な持ち駒を取得しようとしています")
        }
    }

    //(駒の名前,手番,ヒントの表示)を返す
    fun getCellInformation(x:Int,y:Int):Triple<String,Int,Boolean>{
        val cell = boardRepository.getCellInformation(x,y)

        return Triple(cell.piece.nameJP, cell.turn, cell.hint)
    }
    //(駒の名前,手番,ヒントの表示)を返す
    fun getCellTrun(x:Int,y:Int):Int{
        val cell = boardRepository.getCellInformation(x,y)
        val turn =
            if(cell.hint){
                3
            }else if(cell.turn == 1){
                1
            }else if(cell.turn == 2){
                2
            }else{
                4
            }
        return turn
    }

    fun getPieceHand(turn:Int):MutableList<Pair<Piece,Int>>{
        val hold = mutableListOf<Pair<Piece,Int>>()
        var i = 0
        boardRepository.getAllHoldPiece(turn).forEach{piece,count ->
            hold.add(i,Pair(piece,count))
            i++
        }
        return hold
    }

    fun getTurn():Int{
        return turn
    }
}
