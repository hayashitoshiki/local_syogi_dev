package com.example.local_syogi.syogibase.presentation.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.local_syogi.R
import com.example.local_syogi.presentation.view.game.GamePlayBackFragment
import com.example.local_syogi.syogibase.data.game.GameLog
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.syogibase.util.IntUtil.BLACK
import com.example.local_syogi.syogibase.util.IntUtil.WHITE

class GameActivity : AppCompatActivity() {

    var frame: FrameLayout? = null
    private lateinit var view: GameView
    private var first = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (first) {
            frame = this.findViewById(R.id.frame) as FrameLayout
            view = GameView(this, this, frame!!.width, frame!!.height)
            frame!!.addView(view, 0)
            first = false
        }
    }

    // 投了ボタン
    fun surrenderBlack(v: View) {
        surrender(WHITE)
    }
    fun surrenderWhite(v: View) {
        surrender(BLACK)
    }
    private fun surrender(winner: Int) {
        AlertDialog.Builder(this).setCancelable(false)
            .setMessage("投了しますか？")
            .setPositiveButton("はい") { _, _ ->
                gameEnd(winner)
            }
            .setNegativeButton("いいえ", null)
            .create()
            .show()
    }

    var log = mutableListOf<GameLog>()
    // ゲーム終了後画面
    fun gameEnd(winner: Int) {
        log = view.getLog(winner)
        Log.d("Main", "(activity)サイズ：" + log.size)
        val button: Button = findViewById(R.id.backStartButton)
        val button2: Button = findViewById(R.id.surrender_black)
        val viewGroup = this.findViewById(R.id.frame2) as FrameLayout
        val endView: View = layoutInflater.inflate(R.layout.modal_game_end, viewGroup)
        val winLoseView: View = WinLoseModal(this, winner)
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        button.visibility = View.GONE
        button2.visibility = View.GONE
        frame!!.addView(winLoseView, 1)
        // アラートダイアログ入れる時は2にする
        endView.startAnimation(animation)
    }

    // 終了ボダン
    fun end(v: View) {
        finish()
    }
    // 感想戦ボタン
    fun replay(v: View) {
        val shared = GameSettingSharedPreferences(this)
        val gameDetail = GameDetailSetitngModel(
            shared.getHandyBlack(),
            shared.getHandyWhite()
        )
        val button: Button = findViewById(R.id.backStartButton)
        val button2: Button = findViewById(R.id.surrender_black)
        button.visibility = View.GONE
        button2.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fade_in_slide_from_right,
                0
            )
            .replace(R.id.frame, GamePlayBackFragment(log, gameDetail))
            .commit()
    }

    // 終了
    fun end() {
        finish()
    }

    // もう一度ボタン
    fun restart(v: View) {
        finish()
        val intent = Intent(this, this.javaClass)
        startActivity(intent)
    }

    // 戻るボタンの無効化
    override fun onBackPressed() {}
}