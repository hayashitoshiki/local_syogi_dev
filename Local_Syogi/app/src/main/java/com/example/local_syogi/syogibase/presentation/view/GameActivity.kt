package com.example.local_syogi.syogibase.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import com.example.local_syogi.R
import androidx.appcompat.app.AlertDialog


class GameActivity : AppCompatActivity() {

    var frame:FrameLayout? = null
    lateinit var view:GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        frame = this.findViewById(R.id.frame) as FrameLayout
        val view: View = GameView(this,this, frame!!.width,frame!!.height)
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
