package com.example.local_syogi.syogibase.data.game

object GameMode {
    var checkmate = false
    var annan = false
    var pieceLimit = false
    var pieceLimitCount = 3
    var twoTimes = false

    fun getCheckmateMode():Boolean{
        return checkmate

    }

    fun getAnnanMode():Boolean{
        return annan
    }

    fun reset(){
        checkmate = false
        annan = false
        pieceLimit = false
        pieceLimitCount = 3
        twoTimes = false
    }

    fun getModeText():String{
        var title = "本将棋"
        if(annan){
            title = "安南将棋"
        }
        if(checkmate){
            if(title != ""){
                return "カオス将棋"
            }
            title = "王手将棋"
        }
        if(pieceLimit){
            if(title != ""){
                return "カオス将棋"
            }
            title = "持ち駒制限将棋"
        }
        if(twoTimes){
            if(title != ""){
                return "カオス将棋"
            }
            title = "２手差し将棋"
        }
        return title
    }
}