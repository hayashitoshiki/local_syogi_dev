package com.example.local_syogi.syogibase.data.repository

import android.util.Log
import com.example.local_syogi.syogibase.data.game.GameLog
import com.example.local_syogi.syogibase.data.game.GameMode
import com.example.local_syogi.syogibase.data.remote.Player
import com.google.gson.Gson
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URI
import java.net.URISyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * socket.io通信クラス
 */

class SocketRepositoryImp(val presenter: SocketRepository.presenter) :
    SocketRepository {
    private lateinit var socket: Socket
    private lateinit var player: Player

    fun start() {
        val socketUrl = "https://socket-sample-th.herokuapp.com/"
        val uri = URI(socketUrl)
        player = Player("testA", GameMode.getModeInt())

        // 接続
        try {
            socket = IO.socket(uri)
        } catch (e: URISyntaxException) {
            Log.d("MainActivity", "error")
            throw RuntimeException(e)
        }
        Log.d("MainActivity", "取得成功")
        socket.connect()

        // Socket処理定義
        socket.on(Socket.EVENT_CONNECT, connection)
        socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.on("onGameStart", onGameStart)
        socket.on("onMove", onMove)
        socket.on("onGameEnd", onGameEnd)
    }

    // 接続成功
    private val connection = Emitter.Listener {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                Log.d("MainActivity", "接続成功")
                val json = Gson().toJson(player)
                socket.emit("joinRoom", json)
            }
        }
    }

    // 接続失敗
    private val onConnectError = Emitter.Listener {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                Log.d("MainActivity", "接続失敗")
            }
        }
    }

    // 対局開始
    private val onGameStart = Emitter.Listener { data ->
        GlobalScope.launch(Dispatchers.Main) {
            val data2 = data[0].toString()
            val turn = data2.toInt()
            presenter.socketStartGame(turn)
        }
    }

    // 対局終了
    private val onGameEnd = Emitter.Listener { data ->
        GlobalScope.launch(Dispatchers.Main) {
            val data2 = data[0].toString()
            val winnerTurn = data2.toInt()
            presenter.socketGameEnd(winnerTurn)
        }
    }

    // 相手が指した手を受信する
    private val onMove = Emitter.Listener { json ->
        val data2 = json[0].toString()
        val company = Gson().fromJson(data2, GameLog::class.java)
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                val (oldY, oldX) =
                    when (company.oldY) {
                        -1 -> Pair(10, company.oldX)
                        10 -> Pair(-1, company.oldX)
                        else -> Pair(8 - company.oldY, 8 - company.oldX)
                    }
                val newX = 8 - company.newX
                val newY = 8 - company.newY
                val evolution = company.evolution
                presenter.socketMove(oldX, oldY, newX, newY, evolution)
            }
        }
    }

    // 動かした手を送信する
    override fun moveEmit(gameLog: GameLog) {
        val json = Gson().toJson(gameLog)
        socket.emit("move", json)
    }

    // 勝敗を送信する
    override fun gameEndEmit(turn: Int) {
        socket.emit("gameEnd", turn)
    }

    // Activity破棄
    fun onDestroy() {
        socket.disconnect()
        socket.off(Socket.EVENT_CONNECT, connection)
        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
        socket.off("onGameStart", onGameStart)
        socket.off("onMove", onMove)
        socket.off("onGameEnd", onGameEnd)
    }
}