package com.example.socketiosample.javaWebsocket

import android.app.Activity
import android.util.Log
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI


/**
 * Created by naoikotaro on 2018/03/27.
 */
class MyWebSocketClient(val activity: Activity, uri: URI) : WebSocketClient(uri) {


    private val breakLine = System.lineSeparator()


    override fun onOpen(handshakedata: ServerHandshake?) {
        Log.d(javaClass.simpleName, "WSサーバに接続しました。")
        Log.i(javaClass.simpleName, "スレッド：「${Thread.currentThread().name}」で実行中")

    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
        Log.d(javaClass.simpleName, "WSサーバから切断しました。reason:${reason}")
        Log.i(javaClass.simpleName, "スレッド：「${Thread.currentThread().name}」で実行中")
    }

    override fun onMessage(message: String?) {
        Log.d(javaClass.simpleName, "メッセージを受け取りました。")
        Log.i(javaClass.simpleName, "スレッド：「${Thread.currentThread().name}」で実行中")
        activity.runOnUiThread {

            Log.d(javaClass.simpleName, "メッセージをTextViewに追記しました。")
            Log.i(javaClass.simpleName, "スレッド：「${Thread.currentThread().name}」で実行中")

        }
    }

    override fun onError(ex: Exception?) {
        Log.i(javaClass.simpleName, "エラーが発生しました。", ex)
        Log.i(javaClass.simpleName, "スレッド：「${Thread.currentThread().name}」で実行中")
    }
}