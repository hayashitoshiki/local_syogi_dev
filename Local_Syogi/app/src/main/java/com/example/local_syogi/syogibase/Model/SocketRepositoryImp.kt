package com.example.local_syogi.syogibase.Model

import android.util.Log
import com.example.syogibase.Model.Data.GameLog
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URI
import java.net.URISyntaxException



class SocketRepositoryImp(val presenter:SocketRepository.presenter) :SocketRepository{
    private lateinit var socket: Socket

    fun start(){
        val socketUrl ="https://socket-sample-th.herokuapp.com/"
        val uri = URI(socketUrl)

        //接続
        try {
            socket = IO.socket(uri)
        }catch (e: URISyntaxException) {
            Log.d("MainActivity","error")
            throw  RuntimeException(e)
        }
        Log.d("MainActivity","取得成功")
        socket.connect()

        //Socket処理定義
        socket.on(Socket.EVENT_CONNECT,connection)
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.on("onGameEnd",onGameEnd)
        socket.on("move",move)
        socket.on("startGame",onGameStart)
    }

    //接続成功
    private val connection = Emitter.Listener {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                Log.d("MainActivity","接続成功")
                socket.emit("joinRoom","room1")
            }
        }
    }

    //接続失敗
    private val onConnectError = Emitter.Listener {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                Log.d("MainActivity", "接続失敗")
            }
        }
    }

    //対局開始
    private val onGameStart = Emitter .Listener { data ->
        GlobalScope.launch(Dispatchers.Main) {
            val data2 = data[0].toString()
            val turn = data2.toInt()
            presenter.socketStartGame(turn)
        }
    }

    //対局終了
    private val onGameEnd = Emitter .Listener {
        GlobalScope.launch(Dispatchers.Main) {
                presenter.socketGameEnd(1)
        }
    }

    //相手が指す
    private val move = Emitter.Listener { json ->
        Log.d("MainActivity","相手が指しましたのを受信しました")
        val data2 = json[0].toString()
        val company = Gson().fromJson(data2, GameLog::class.java)
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val oldX = 8 - company.oldX
                val oldY = 8 - company.oldY
                val newX = 8 - company.newX
                val newY = 8 - company.newY
                Log.d(
                    "MainActivity",
                    "oldX：" + oldX + ",oldY:" + oldY + ",newX:" + newX + ",newY:" + newY
                )
                presenter.socketMove(oldX, oldY, newX, newY)
            }
        }
    }

    //動かした手を送信する
    override fun moveEmit(gameLog: GameLog){
        val json = Gson().toJson(gameLog)
        socket.emit("moveServer",json)
    }

    //勝敗を送信する
    override fun gameEndEmit(turn: Int) {
        socket.emit("game-end",turn)
    }

    fun onDestroy(){
        socket.disconnect()
        socket.off(Socket.EVENT_CONNECT,connection)
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.off("onGameEnd",onGameEnd)
        socket.off("move",move)
        socket.off("startGame",onGameStart)
    }

}