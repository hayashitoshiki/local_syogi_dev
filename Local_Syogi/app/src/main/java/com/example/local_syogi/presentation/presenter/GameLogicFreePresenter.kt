package com.example.local_syogi.presentation.presenter

import com.example.local_syogi.presentation.contact.GameViewRateContact
import com.example.local_syogi.syogibase.data.local.GameLog
import com.example.local_syogi.syogibase.domain.SyogiLogicUseCase
import com.example.local_syogi.syogibase.presentation.contact.GameViewContact


class GameLogicFreePresenter(private val view: GameViewRateContact.View, private val syogiUseCase: SyogiLogicUseCase): GameViewRateContact.Presenter{

    companion object {
        const val BLACK = 1
        const val WHITE = 2
        const val HINT = 3
    }

    //タッチ判定
    override fun onTouchEvent(x:Int, y:Int) {}

    //将棋描画
    override fun drawView(){
        view.drawBoard()
        //盤上の駒セット
        for (i in 0..8) for (j in 0..8) {
            val(pieceName, turn, hint) = syogiUseCase.getCellInformation(i, j)
            when(turn){
                BLACK -> view.drawBlackPiece(pieceName,i,j)
                WHITE -> view.drawWhitePiece(pieceName,i,j)
            }
            if(hint)view.drawHint(i,j)
        }
        //順番に見て駒と枚数を返す 駒と枚数  表示するリストを作ってその添え字と返ってきた枚数。0以外なら
        syogiUseCase.getPieceHand(BLACK).forEachIndexed{ index, piece ->
            if(piece.second != 0) {
                view.drawHoldPieceBlack(piece.first.nameJP, piece.second, index)
            }
        }
        syogiUseCase.getPieceHand(WHITE).forEachIndexed{ index, piece ->
            if(piece.second != 0) {
                view.drawHoldPieceWhite(piece.first.nameJP, piece.second, index)
            }
        }
    }

    //成り判定
    override fun evolutionPiece(bool:Boolean){}


    //駒の動きを受信。受信側は判定を行わない　　viewの変更
    override fun socketMove(oldX:Int, oldY:Int, newX:Int, newY:Int, evolution:Boolean){
        syogiUseCase.setPre(oldX,oldY)
        syogiUseCase.setMove(newX,newY, evolution)
        val turn = syogiUseCase.getTurn()
        if(turn == BLACK){
            syogiUseCase.setTurn(WHITE)
        }else{
            syogiUseCase.setTurn(BLACK)
        }
        view.playbackEffect()
    }

    fun setBackMove(){
        syogiUseCase.setBackMove()
        val turn = syogiUseCase.getTurn()
        if(turn == BLACK){
            syogiUseCase.setTurn(WHITE)
        }else{
            syogiUseCase.setTurn(BLACK)
        }
    }
    //ターン変更
    override fun setTurn(turn:Int){}

}