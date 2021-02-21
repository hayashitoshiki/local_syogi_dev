package com.example.local_syogi.presentation.view.game

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
import com.example.local_syogi.syogibase.data.entity.game.GameLog
import com.example.local_syogi.syogibase.data.repository.SocketRepository
import com.example.local_syogi.syogibase.data.repository.SocketRepositoryImp
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.syogibase.presentation.view.GameSettingSharedPreferences
import com.example.local_syogi.syogibase.presentation.view.WinLoseModal
import com.example.local_syogi.syogibase.util.IntUtil
import java.text.SimpleDateFormat
import java.util.*

class GameRateActivity : AppCompatActivity(), SocketRepository.presenter {

    var frame: FrameLayout? = null
    lateinit var view: GameRateView
    private lateinit var socketRepository: SocketRepositoryImp
    private lateinit var button2: Button
    var log = mutableListOf<GameLog>()
    private var isBackButton = true
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
        setContentView(R.layout.activity_game_rate)
        socketRepository = SocketRepositoryImp(this)
        socketRepository.start()
        // 画面作成
        frame = this.findViewById(R.id.frame) as FrameLayout
        button2 = findViewById(R.id.surrender_black)
        button2.visibility = View.INVISIBLE

        val sharedPreferences = GameSettingSharedPreferences(this)
        val rateTimeLimit = sharedPreferences.getRateTimeLimit().toLong()
        countNumberWhite = rateTimeLimit
        countNumberBlack = rateTimeLimit
        timerWhite = findViewById(R.id.timerWhite)
        timerBlack = findViewById(R.id.timerBlack)
        timerWhite.visibility = View.INVISIBLE
        timerBlack.visibility = View.INVISIBLE
        timerWhite.text = dataFormat.format(countNumberWhite)
        timerBlack.text = dataFormat.format(countNumberBlack)
    }

    // 投了ボタン
    fun surrenderBlack(v: View) {
        surrender(2)
    }
    private fun surrender(turn: Int) {
        AlertDialog.Builder(this)
            .setMessage("投了しますか？")
            .setPositiveButton("はい") { _, _ ->
                gameEnd(turn, 2)
                gameEndEmit(turn, 2)
            }
            .setNegativeButton("いいえ", null)
            .show()
    }

    // ゲーム終了後画面
    fun gameEnd(winner: Int, winType: Int) {
        log = view.getLog(winner, winType)
        val viewGroup = this.findViewById(R.id.frame2) as FrameLayout
        val endView: View = layoutInflater.inflate(R.layout.modal_game_end, viewGroup)
        val winLoseView: View = WinLoseModal(this, winner)
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        button2.visibility = View.INVISIBLE
        timerWhite.visibility = View.INVISIBLE
        timerBlack.visibility = View.INVISIBLE
        frame!!.addView(winLoseView, 1)
        endView.startAnimation(animation)
    }

    // 終了ボダン
    fun end(v: View) {
        finish()
    }
    // 終了
    fun end() {
        finish()
    }

    // もう一度ボタン
    fun restart(v: View) {
        finish()
        val intent = Intent(this, GameRateActivity()::class.java)
        startActivity(intent)
    }
    // 感想戦ボタン
    fun replay(v: View) {
        val shared = GameSettingSharedPreferences(this)
        val gameDetail = GameDetailSetitngModel(
            shared.getHandyBlack(),
            shared.getHandyWhite()
        )
        button2.visibility = View.GONE
        timerWhite.visibility = View.GONE
        timerBlack.visibility = View.GONE
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fade_in_slide_from_right,
                0
            )
            .replace(R.id.frame, GamePlayBackFragment(log, gameDetail))
            .commit()
    }

    // 戻るボタンの無効化
    override fun onBackPressed() {
        if (isBackButton) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        socketRepository.onDestroy()
        Log.d("MainActivity", "socketオフ")
    }
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (first) {
            frame = this.findViewById(R.id.frame) as FrameLayout
            view = GameRateView(this, this, frame!!.width, frame!!.height)
            first = false
        }
    }
    // 対局開始を受信　自動的 activityの変更
    override fun socketStartGame(turn: Int, whiteName: String) {
        isBackButton = false
        frame!!.addView(view, 0)
        button2.visibility = View.VISIBLE
        timerWhite.visibility = View.VISIBLE
        timerBlack.visibility = View.VISIBLE
        view.setStartTurn(turn, whiteName)
    }

    // socketで受信した手の受け取り
    override fun socketMove(oldX: Int, oldY: Int, newX: Int, newY: Int, evolution: Boolean, countNumberWhite: Long) {
        view.socketMove(oldX, oldY, newX, newY, evolution)
        this.countNumberWhite = countNumberWhite
        changeTimerBlack()
    }

    // socketで受信した勝敗結果の受け取り
    override fun socketGameEnd(turn: Int, winType: Int) {
        gameEnd(turn, winType)
    }

    // 投了通知→勝敗結果通知
    fun gameEndEmit(turn: Int, winType: Int) {
        socketRepository.gameEndEmit(turn, winType)
    }

    // 指した手を送信
    fun moveEmit(log: GameLog) {
        changeTimerWhite()
        socketRepository.moveEmit(log, countNumberBlack)
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

    // タイマー機能
    internal inner class CountDownWhite(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {
        // 時間切れ
        override fun onFinish() {
            timerWhite.text = dataFormat.format(0)
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
            gameEnd(IntUtil.WHITE, 4)
            gameEndEmit(IntUtil.BLACK, 4)
        }
        // インターバルで呼ばれる
        override fun onTick(millisUntilFinished: Long) {
            timerBlack.text = dataFormat.format(millisUntilFinished)
            countNumberBlack = millisUntilFinished
        }
    }
}
