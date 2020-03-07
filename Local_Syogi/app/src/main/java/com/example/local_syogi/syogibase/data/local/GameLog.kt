package com.example.local_syogi.syogibase.data.local

import com.example.local_syogi.syogibase.util.Piece

//１局の対局ログ格納クラス

class GameLog(val oldX:Int, var oldY:Int, val afterPiece: Piece, val afterTurn:Int,
              val newX:Int, val newY:Int, val beforpiece:Piece, val beforturn:Int, var evolution:Boolean)