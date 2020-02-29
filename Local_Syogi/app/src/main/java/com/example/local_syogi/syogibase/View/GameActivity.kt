package com.example.syogibase.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import com.example.local_syogi.R
import com.example.local_syogi.com.example.syogibase.View.WinLoseModal
import android.content.DialogInterface
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.syogibase.Model.Data.GameLog
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URI
import java.net.URISyntaxException
import org.json.JSONObject
import org.json.JSONArray
import com.google.gson.Gson
import com.google.android.gms.drive.metadata.CustomPropertyKey.fromJson
import com.google.android.gms.games.Game


class GameActivity : AppCompatActivity() {

    var frame:FrameLayout? = null
    lateinit var view:GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        //画面作成
        view = GameView(this,this)
        frame = this.findViewById(R.id.frame) as FrameLayout
        frame!!.addView(view, 0)
    }

    //投了ボタン
    fun surrenderBlack(v:View){
        surrender(2)
    }
    fun surrenderWhite(v:View){
        surrender(1)
    }
    private fun surrender(turn:Int){
        AlertDialog.Builder(this)
            .setMessage("投了しますか？")
            .setPositiveButton("はい") { _, _ ->
                gameEnd(turn)
            }
            .setNegativeButton("いいえ", null)
            .show()
    }

    //ゲーム終了後画面
    fun gameEnd(turn:Int){
        val button:Button = findViewById(R.id.surrender_white)
        val button2:Button = findViewById(R.id.surrender_black)
        val viewGroup = this.findViewById(R.id.frame2) as FrameLayout
        val endView:View = layoutInflater.inflate(R.layout.modal_game_end, viewGroup)
        val winLoseView: View = WinLoseModal(this, turn)
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        button.visibility = View.INVISIBLE
        button2.visibility = View.INVISIBLE
        frame!!.addView(winLoseView, 1)
        endView.startAnimation(animation)
    }

    //終了ボダン
    fun end(v: View) {
        finish()
    }

    //もう一度ボタン
    fun restart(v: View) {
        finish()
        val intent = Intent(this, this.javaClass)
        startActivity(intent)
    }

    //戻るボタンの無効化
    override fun onBackPressed() {}
}
