package com.example.local_syogi.syogibase.data.repository

import android.util.Log
import com.example.local_syogi.di.MyApplication
import com.example.local_syogi.syogibase.data.entity.game.GameLog
import com.example.local_syogi.syogibase.data.entity.game.GameMode
import com.example.local_syogi.syogibase.data.entity.remote.DtoEndInfo
import com.example.local_syogi.syogibase.data.entity.remote.DtoMove
import com.example.local_syogi.syogibase.data.entity.remote.DtoPlayer
import com.example.local_syogi.syogibase.presentation.view.GameSettingSharedPreferences
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
    private lateinit var player: DtoPlayer

    fun start() {
        val socketUrl = "https://socket-sample-th.herokuapp.com/"
        val uri = URI(socketUrl)
        val sharedPreferences = GameSettingSharedPreferences(MyApplication.getInstance().applicationContext)
        player = DtoPlayer(
            "testA",
            GameMode.getModeInt() + sharedPreferences.getRateTimeLimit()
        )

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
            presenter.socketStartGame(turn, "socket敵")
        }
    }

    // 対局終了
    private val onGameEnd = Emitter.Listener { json ->
        GlobalScope.launch(Dispatchers.Main) {
            val data = json[0].toString()
            val dtoMove = Gson().fromJson(data, DtoEndInfo::class.java)
            presenter.socketGameEnd(dtoMove.turn, dtoMove.winType)
        }
    }

    // 相手が指した手を受信する
    private val onMove = Emitter.Listener { json ->
        val data = json[0].toString()
        val dtoMove = Gson().fromJson(data, DtoMove::class.java)
        GlobalScope.launch(Dispatchers.Main) {
                val (oldY, oldX) =
                    when (dtoMove.toY) {
                        -1 -> Pair(10, dtoMove.toX)
                        10 -> Pair(-1, dtoMove.toX)
                        else -> Pair(8 - dtoMove.toY, 8 - dtoMove.toX)
                    }
                val newX = 8 - dtoMove.fromX
                val newY = 8 - dtoMove.fromY
                val evolution = dtoMove.evolution
                val countNumberBlack = dtoMove.countNumber
                presenter.socketMove(oldX, oldY, newX, newY, evolution, countNumberBlack)
        }
    }

    // 動かした手を送信する
    override fun moveEmit(gameLog: GameLog, countNumberBlack: Long) {
        val jsonData = DtoMove(
            gameLog.oldX,
            gameLog.oldY,
            gameLog.afterPiece,
            gameLog.afterTurn,
            gameLog.newX,
            gameLog.newY,
            gameLog.beforpiece,
            gameLog.beforturn,
            gameLog.evolution,
            countNumberBlack
        )
        val json = Gson().toJson(jsonData)
        socket.emit("move", json)
    }

    // 勝敗を送信する
    override fun gameEndEmit(turn: Int, winType: Int) {
        val jsonData =
            DtoEndInfo(
                turn,
                winType
            )
        val json = Gson().toJson(jsonData)
        socket.emit("gameEnd", json)
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