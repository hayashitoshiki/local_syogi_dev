package com.example.local_syogi.syogibase.presentation.view

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.local_syogi.R
import com.example.local_syogi.presentation.view.game.GamePlayBackFragment
import com.example.local_syogi.syogibase.data.entity.game.GameLog
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.syogibase.util.IntUtil.BLACK
import com.example.local_syogi.syogibase.util.IntUtil.WHITE
import java.text.SimpleDateFormat
import java.util.*

class GameActivity : AppCompatActivity() {

    var frame: FrameLayout? = null
    private lateinit var view: GameView
    private var first = true
    private val dataFormat = SimpleDateFormat("mm:ss", Locale.US)
    private var countDownTimerWhite: CountDownWhite? = null
    private var countDownTimerBlack: CountDownBlack? = null
    private var countNumberWhite: Long = 0
    private var countNumberBlack: Long = 0
    private lateinit var timerWhite: TextView
    private lateinit var timerBlack: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val sharedPreferences = GameSettingSharedPreferences(this)
        val minuteBlack = sharedPreferences.getMinuteBlack().toLong() * 60 * 1000
        val secondBlack = sharedPreferences.getSecondBlack().toLong() * 1000
        val minuteWhite = sharedPreferences.getMinuteWhite().toLong() * 60 * 1000
        val secondWhite = sharedPreferences.getSecondWhite().toLong() * 1000
        countNumberWhite = secondWhite + minuteWhite
        countNumberBlack = secondBlack + minuteBlack
        timerWhite = findViewById(R.id.timerWhite)
        timerBlack = findViewById(R.id.timerBlack)
        timerWhite.text = dataFormat.format(countNumberWhite)
        timerBlack.text = dataFormat.format(countNumberBlack)
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
    // 手番(タイマー)チェンジ
    fun changeTimerBlack() {
        countDownTimerWhite?.cancel()
        countDownTimerBlack = CountDownBlack(countNumberBlack, 10) // countMillisを残り時間にセット
        countDownTimerBlack!!.start() // タイマーをスタート
    }
    fun changeTimerWhite() {
        countDownTimerBlack?.cancel()
        countDownTimerWhite = CountDownWhite(countNumberWhite, 10) // countMillisを残り時間にセット
        countDownTimerWhite!!.start() // タイマーをスタート
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
                gameEnd(winner, 2)
            }
            .setNegativeButton("いいえ", null)
            .create()
            .show()
    }

    var log = mutableListOf<GameLog>()
    // ゲーム終了後画面
    fun gameEnd(winner: Int, winType: Int) {
        log = view.getLog(winner, winType)
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

    // 中断ボタン
    fun pauseButton(v: View) {
        AlertDialog.Builder(this).setCancelable(false)
            .setMessage("中止しますか？")
            .setPositiveButton("はい") { _, _ ->
                finish()
            }
            .setNegativeButton("いいえ", null)
            .create()
            .show()
    }

    // 戻るボタンの無効化
    override fun onBackPressed() {}

    // タイマー機能
    internal inner class CountDownWhite(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        // 時間切れ
        override fun onFinish() {
            timerWhite.text = dataFormat.format(0)
            gameEnd(BLACK, 4)
        }
        // インターバルで呼ばれる
        override fun onTick(millisUntilFinished: Long) {
            timerWhite.text = dataFormat.format(millisUntilFinished)
            countNumberWhite = millisUntilFinished
        }
    }
    internal inner class CountDownBlack(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        // 時間切れ
        override fun onFinish() {
            timerBlack.text = dataFormat.format(0)
            gameEnd(WHITE, 4)
        }
        // インターバルで呼ばれる
        override fun onTick(millisUntilFinished: Long) {
            timerBlack.text = dataFormat.format(millisUntilFinished)
            countNumberBlack = millisUntilFinished
        }
    }
}
