package com.example.local_syogi.presentation.view.game

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
import com.example.local_syogi.syogibase.data.game.GameLog
import com.example.local_syogi.syogibase.data.repository.SocketRepository
import com.example.local_syogi.syogibase.data.repository.SocketRepositoryImp
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.syogibase.presentation.view.GameSettingSharedPreferences
import com.example.local_syogi.syogibase.presentation.view.WinLoseModal

class GameRateActivity : AppCompatActivity(), SocketRepository.presenter {

    var frame: FrameLayout? = null
    lateinit var view: GameRateView
    private lateinit var socketRepository: SocketRepositoryImp
    private lateinit var button2: Button
    var log = mutableListOf<GameLog>()
    private var isBackButton = true
    private var first = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_rate)
        socketRepository =
            SocketRepositoryImp(this)
        socketRepository.start()
        // 画面作成
        frame = this.findViewById(R.id.frame) as FrameLayout
        button2 = findViewById(R.id.surrender_black)
        button2.visibility = View.INVISIBLE
    }

    // 投了ボタン
    fun surrenderBlack(v: View) {
        surrender(2)
    }
    private fun surrender(turn: Int) {
        AlertDialog.Builder(this)
            .setMessage("投了しますか？")
            .setPositiveButton("はい") { _, _ ->
                gameEnd(turn)
                gameEndEmit(turn)
            }
            .setNegativeButton("いいえ", null)
            .show()
    }

    // ゲーム終了後画面
    fun gameEnd(winner: Int) {
        log = view.getLog(winner)
        val viewGroup = this.findViewById(R.id.frame2) as FrameLayout
        val endView: View = layoutInflater.inflate(R.layout.modal_game_end, viewGroup)
        val winLoseView: View = WinLoseModal(this, winner)
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        button2.visibility = View.INVISIBLE
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
        val button: Button = findViewById(R.id.surrender_black)
        button.visibility = View.GONE
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
    override fun socketStartGame(turn: Int) {
        isBackButton = false
        frame!!.addView(view, 0)
        button2.visibility = View.VISIBLE
        view.setTurn(turn)
    }
    // socketで受信した手の受け取り
    override fun socketMove(oldX: Int, oldY: Int, newX: Int, newY: Int, evolution: Boolean) {
        view.socketMove(oldX, oldY, newX, newY, evolution)
    }
    // socketで受信した勝敗結果の受け取り
    override fun socketGameEnd(turn: Int) {
        gameEnd(turn)
    }
    // 投了通知→勝敗結果通知
    fun gameEndEmit(turn: Int) {
        socketRepository.gameEndEmit(turn)
    }
    // 指した手を送信
    fun moveEmit(log: GameLog) {
        socketRepository.moveEmit(log)
    }
}