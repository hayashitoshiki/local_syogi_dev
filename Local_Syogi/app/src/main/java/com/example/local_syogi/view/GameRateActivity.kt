package com.example.local_syogi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import com.example.local_syogi.R
import com.example.local_syogi.com.example.syogibase.View.WinLoseModal
import com.example.local_syogi.syogibase.Model.SocketRepository
import com.example.local_syogi.syogibase.Model.SocketRepositoryImp
import com.example.syogibase.Model.Data.GameLog
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout


class GameRateActivity : AppCompatActivity(), SocketRepository.presenter{

    var frame: FrameLayout? = null
    lateinit var view: GameRateView
    private lateinit var socketRepository: SocketRepositoryImp
    private lateinit var button2: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_rate)
        socketRepository = SocketRepositoryImp(this)
        socketRepository.start()
        //画面作成
        frame = this.findViewById(R.id.frame) as FrameLayout
        button2 = findViewById(R.id.surrender_black)
        button2.visibility = View.INVISIBLE

    }

    //投了ボタン
    fun surrenderBlack(v: View){
        surrender(2)
    }
    private fun surrender(turn:Int){
        AlertDialog.Builder(this)
            .setMessage("投了しますか？")
            .setPositiveButton("はい") { _, _ ->
                gameEnd(turn)
                gameEndEmit(turn)
            }
            .setNegativeButton("いいえ", null)
            .show()
    }

    //ゲーム終了後画面
    fun gameEnd(turn:Int){
        val viewGroup = this.findViewById(R.id.frame2) as FrameLayout
        val endView: View = layoutInflater.inflate(R.layout.modal_game_end, viewGroup)
        val winLoseView: View = WinLoseModal(this, turn)
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

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
        val intent = Intent(this, GameRateActivity()::class.java)
        startActivity(intent)
    }

    //戻るボタンの無効化
    override fun onBackPressed() {}

    override fun onDestroy() {
        super.onDestroy()
        socketRepository.onDestroy()
        Log.d("MainActivity","socketオフ")
    }

    //対局開始を受信　自動的 activityの変更
    override fun socketStartGame(turn:Int){
        view = GameRateView(this,this)
        frame!!.addView(view, 0)
        button2.visibility = View.VISIBLE
        view.setTurn(turn)
    }
    //駒の動きを受信。受信側は判定を行わない　　viewの変更
    override fun socketMove(oldX:Int, oldY:Int, newX:Int, newY:Int){
        view.socketMove(oldX, oldY, newX, newY)
    }
    //勝敗結果を受信
    override fun socketGameEnd(turn:Int){
        gameEnd(turn)
    }
    //投了通知→勝敗結果通知
    fun gameEndEmit(turn:Int){
        socketRepository.gameEndEmit(turn)
    }
    fun moveEmit(log: GameLog){
        socketRepository.moveEmit(log)
    }

}
