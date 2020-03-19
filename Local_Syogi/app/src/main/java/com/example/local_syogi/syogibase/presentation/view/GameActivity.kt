package com.example.local_syogi.syogibase.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import com.example.local_syogi.R
import androidx.appcompat.app.AlertDialog
import com.example.local_syogi.presentation.view.game.GameFreeView
import com.example.local_syogi.presentation.view.game.GamePlayBackFragment
import com.example.local_syogi.syogibase.data.local.GameLog






class GameActivity : AppCompatActivity() {

    var frame:FrameLayout? = null
    private lateinit var view:GameView
    private var first = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(first) {
            frame = this.findViewById(R.id.frame) as FrameLayout
            view = GameView(this, this, frame!!.width, frame!!.height)
            frame!!.addView(view, 0)
            first = false
        }
    }

    //投了ボタン
    fun surrenderBlack(v:View){
        surrender(2)
    }
    fun surrenderWhite(v:View){
        surrender(1)
    }
    private fun surrender(turn:Int){
        AlertDialog.Builder(this).setCancelable(false)
            .setMessage("投了しますか？")
            .setPositiveButton("はい") { _, _ ->
                gameEnd(turn)
            }
            .setNegativeButton("いいえ", null)
            .create()
            .show()

    }

    var log = mutableListOf<GameLog>()
    //ゲーム終了後画面
    fun gameEnd(turn:Int){
        log = view.getLog()
        Log.d("Main","(activity)サイズ："+ log.size)
        val button:Button = findViewById(R.id.backStartButton)
        val button2:Button = findViewById(R.id.surrender_black)
        val viewGroup = this.findViewById(R.id.frame2) as FrameLayout
        val endView:View = layoutInflater.inflate(R.layout.modal_game_end, viewGroup)
        val winLoseView: View = WinLoseModal(this, turn)
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        button.visibility = View.GONE
        button2.visibility = View.GONE
        frame!!.addView(winLoseView,1)
        //アラートダイアログ入れる時は2にする
        endView.startAnimation(animation)
    }

    //終了ボダン
    fun end(v: View) {
        //val playBack = GamePlayBackFragment(log)
       // val view: View = GameFreeView(this,this, frame!!.width,frame!!.height,log)
        val button:Button = findViewById(R.id.backStartButton)
        val button2:Button = findViewById(R.id.surrender_black)
        button.visibility = View.GONE
        button2.visibility = View.GONE
        frame!!.removeAllViews()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame, GamePlayBackFragment(log))
            .addToBackStack(null)
            .commit()
        //frame!!.addView(view)
        //finish()
    }

    //終了
    fun end(){
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
