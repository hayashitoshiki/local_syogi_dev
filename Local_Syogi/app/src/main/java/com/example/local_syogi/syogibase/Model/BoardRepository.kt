package com.example.local_syogi.syogibase.Model


import android.util.Log
import com.example.local_syogi.syogibase.Model.Data.GameMode
import com.example.syogibase.Model.Data.*
import com.example.syogibase.Model.Data.Piece.*


class BoardRepository {
    private val board:Board = Board()
    private val logList = arrayListOf<GameLog>()

    private var previousX:Int = 0
    private var previousY:Int = 0
    private var previousPiece:Piece = None

    companion object {
        const val BLACK = 1
        const val WHITE = 2
    }


    //動かす前の駒の状態をセット
    fun setPre(x:Int, y:Int){
        previousX = x
        previousY = y
        Log.d("Main","駒："+  ",x:" + previousX +",y:" + previousY)
        if(y == 10 || y == -1) previousPiece = changeIntToPiece(x)
        else  previousPiece = board.cells[previousX][previousY].piece
        //Log.d("Main","駒："+ board.cells[x][y].piece + ",x:" + previousX +",y:" + previousY)
    }

    //最新手を返す
    fun getLogList():GameLog{
        return logList.last()
    }

    //駒を動かす
    fun setMove(x:Int,y:Int, turn:Int){
        logList.add(GameLog(previousX,previousY,previousPiece,turn,
                                x,y,board.cells[x][y].piece, board.cells[x][y].turn))
        board.cells[x][y].piece = previousPiece
        board.cells[x][y].turn = turn
        if(previousY == 10 ) board.holdPieceBlack[changeIntToPiece(previousX)] =  board.holdPieceBlack[changeIntToPiece(previousX)]!! - 1
        else if(previousY == -1)board.holdPieceWhite[changeIntToPiece(previousX)] =  board.holdPieceWhite[changeIntToPiece(previousX)]!! - 1
        else {
            board.cells[previousX][previousY].piece = None
            board.cells[previousX][previousY].turn = 0
        }
        Log.d("Main","駒："+ board.cells[x][y].piece)
    }

    //１手戻す
    fun setBackMove(){
        val log:GameLog = logList.last()
        if(log.oldY == 10 ) board.holdPieceBlack[changeIntToPiece(log.oldX)] =  board.holdPieceBlack[changeIntToPiece(log.oldX)]!! + 1
        else if(log.oldY == -1)board.holdPieceWhite[changeIntToPiece(log.oldX)] =  board.holdPieceWhite[changeIntToPiece(log.oldX)]!! + 1
        else {
            board.cells[log.oldX][log.oldY].piece = log.afterPiece
            board.cells[log.oldX][log.oldY].turn = log.afterTurn
        }
        board.cells[log.newX][log.newY].piece = log.beforpiece
        board.cells[log.newX][log.newY].turn  = log.beforturn
        logList.remove(log)
    }

    //成る
    fun setEvolution(){
        val log:GameLog = logList.last()
        board.cells[log.newX][log.newY].piece = log.afterPiece.evolution()
    }

    //成れる駒か判別
    fun findEvolutionBy(x:Int, y:Int):Boolean{
        return board.cells[x][y].piece.findEvolution()
    }

    //打った駒の打つ前のY軸取得
    fun findLogY():Int{
        return logList.last().oldY
    }

    //ヒントが表示されているマスの数を返す
    fun getCountByHint():Int{
        var count=0
        board.cells.forEach { count += it.filter { it.hint }.count() }

        return count
    }

    //持ち駒リスト取得
    fun getAllHoldPiece(turn:Int):Map<Piece,Int>{
        return if(turn == BLACK){
            board.holdPieceBlack
        }else{
            board.holdPieceWhite
        }
    }

    //持ち駒の数を取得
    fun getCountHoldPiece(turn:Int):Int{
        var count = 0
        for ((_,v) in getAllHoldPiece(turn)){
            count += v
        }
        return count
    }

    //持ち駒マスから取得
    fun findHoldPieceBy(i:Int, turn:Int):Piece{
        if(turn == 1 && board.holdPieceBlack[changeIntToPiece(i)] == 0 ||
            turn == 2 && board.holdPieceWhite[changeIntToPiece(i)] == 0)return None

        return changeIntToPiece(i)
    }

    //持ち駒台の座標から駒を取得(本当はよくない)
    private fun changeIntToPiece(i:Int):Piece{
        return when(i){
            2->  FU
            3->  KYO
            4->  KEI
            5->  GIN
            6 -> KIN
            7 -> KAKU
            8 -> HISYA
            else -> None
        }
    }

    //取った駒を表示
    fun getTakePice():Piece{
        return logList.last().beforpiece
    }

    //持ち駒追加
    fun setHoldPiece(){
        val log:GameLog = logList.last()
        if(log.beforpiece != None){
            if(log.beforturn == 2) {
                when (log.beforpiece) {
                    FU, TO -> board.holdPieceBlack[FU] = board.holdPieceBlack[FU]!! + 1
                    KYO, N_KYO -> board.holdPieceBlack[KYO] = board.holdPieceBlack[KYO]!! + 1
                    KEI, N_KEI -> board.holdPieceBlack[KEI] = board.holdPieceBlack[KEI]!! + 1
                    GIN, N_GIN -> board.holdPieceBlack[GIN] = board.holdPieceBlack[GIN]!! + 1
                    KIN -> board.holdPieceBlack[KIN] = board.holdPieceBlack[KIN]!! + 1
                    HISYA, RYU -> board.holdPieceBlack[HISYA] = board.holdPieceBlack[HISYA]!! + 1
                    KAKU, UMA -> board.holdPieceBlack[KAKU] = board.holdPieceBlack[KAKU]!! + 1
                    else -> Log.e("BoaedRepository","不正な駒を取ろうとしています")
                }
            }else if(log.beforturn == 1){
                when (log.beforpiece) {
                    FU, TO -> board.holdPieceWhite[FU] = board.holdPieceWhite[FU]!! + 1
                    KYO, N_KYO -> board.holdPieceWhite[KYO] = board.holdPieceWhite[KYO]!! + 1
                    KEI, N_KEI -> board.holdPieceWhite[KEI] = board.holdPieceWhite[KEI]!! + 1
                    GIN, N_GIN -> board.holdPieceWhite[GIN] = board.holdPieceWhite[GIN]!! + 1
                    KIN -> board.holdPieceWhite[KIN] = board.holdPieceWhite[KIN]!! + 1
                    HISYA, RYU -> board.holdPieceWhite[HISYA] = board.holdPieceWhite[HISYA]!! + 1
                    KAKU, UMA -> board.holdPieceWhite[KAKU] = board.holdPieceWhite[KAKU]!! + 1
                    else ->  Log.e("BoaedRepository","不正な駒を取ろうとしています")
                }
            }
        }
    }

    //強制的にならないといけない駒かチェック
    fun checkForcedevolution():Boolean{
        val log:GameLog = logList.last()
        val piece =  board.cells[log.newX][log.newY].piece
        if((piece == KYO && (((log.newY <= 1 && log.afterTurn == 1) || (7 <= log.newY && log.afterTurn == 2))))||
           (piece == KEI && (((log.newY <= 1 && log.afterTurn == 1) || (7 <= log.newY && log.afterTurn == 2))))||
            piece == FU || piece == HISYA || piece == KAKU)return true
        return false
    }

    //駒の動きを取得
    fun getMove(x:Int, y:Int):Array<Array<PieceMove>>{
        return getPiece(x,y).getMove()
    }

    //ヒントセット
    fun setHint(x:Int, y:Int){
        board.cells[x][y].hint = true
    }

    //ヒントリセット
    fun resetHint(){
        board.cells.forEach { it.forEach { it.hint = false } }
    }

    //そのマスの駒の所有者を返す
    fun getTurn(x:Int, y:Int):Int{
        return board.cells[x][y].turn
    }

    //そのマスのヒントを返す
    fun getHint(x:Int, y:Int):Boolean{
        return board.cells[x][y].hint
    }
    //そのマスの駒を返す
    fun getPiece(x:Int, y:Int):Piece{
        // TODO 安南だったら一段下の駒を取得
        return if(GameMode.getAnnanMode()){
            if(board.cells[x][y].turn == BLACK && y+1 <= 8 && getTurn(x,y+1) == board.cells[x][y].turn){
                 board.cells[x][y+1].piece
            }else if(board.cells[x][y].turn == WHITE && y-1 >= 0 && getTurn(x,y-1) == board.cells[x][y].turn){
                 board.cells[x][y-1].piece
            }else{
                 board.cells[x][y].piece
            }
        }else {
             board.cells[x][y].piece
        }
        //return board.cells[x][y].piece
    }

    //そのマスの駒の名前を返す
    fun getJPName(x:Int, y:Int):String{
        return board.cells[x][y].piece.nameJP
    }

    //指定した手番の王様の座標を返す
    fun findKing(turn:Int):Pair<Int, Int>{
        for(i in 0..8) for(j in 0..8)if(board.cells[i][j].piece == OU && board.cells[i][j].turn == turn)return Pair(i, j)
        return  Pair(0, 0)
    }

    //駒がいける方向を返す

    //上
    fun findUpMovePiece(x:Int, y:Int):Boolean{
        return board.cells[x][y].piece.equalUpMovePiece()
    }
    //下
    fun findDownMovePiece(x:Int, y:Int):Boolean{
        return board.cells[x][y].piece.equalDownMovePiece()
    }
    //横
    fun findLRMovePiece(x:Int, y:Int):Boolean{
        return board.cells[x][y].piece.equalLRMovePiece()
    }
    //斜め上
    fun findDiagonalUp(x:Int, y:Int):Boolean{
        return board.cells[x][y].piece.equalDiagonalUp()
    }
    //斜め下
    fun findDiagonalDown(x:Int, y:Int):Boolean{
        return board.cells[x][y].piece.equalDiagonalDown()
    }
}