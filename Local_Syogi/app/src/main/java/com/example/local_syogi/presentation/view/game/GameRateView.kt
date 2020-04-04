package com.example.local_syogi.presentation.view.game

import android.app.AlertDialog
import android.content.Context
import android.graphics.*
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.game.GameViewRateContact
import com.example.local_syogi.syogibase.data.game.GameLog
import com.example.local_syogi.syogibase.data.game.GameMode
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class GameRateView(private val activity: GameRateActivity, context: Context) : View(context), GameViewRateContact.View,
    KoinComponent {

    val presenter: GameViewRateContact.Presenter by inject { parametersOf(this) }
    private lateinit var canvas: Canvas
    private val paint: Paint = Paint()

    private var bw: Float = 0.0f // 将棋盤の幅
    private var bh: Float = 0.0f // 将棋盤の高さ
    private var cw: Float = bw / 9 // １マスの幅
    private var ch: Float = bh / 9 // １マスの高さ
    private val median = 3 // 盤の位置　中央値：３ 範囲：０～６

    private lateinit var soundPool: SoundPool
    private var soundOne = 0

    // onCreat
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.d("Main", "onDraw")
        bw = width.toFloat() // 将棋盤の幅
        bh = width.toFloat() // 将棋盤の高さ
        cw = bw / 9 // １マスの幅
        ch = bh / 9
        paint.textSize = cw
        val textWidth = paint.measureText(GameMode.getModeText())

        this.canvas = canvas
        canvas.save()
        canvas.rotate(180f, (width / 2).toFloat(), cw * 2)
        canvas.drawText(GameMode.getModeText(), width / 2 - textWidth / 2, cw * 5 / 2, paint)
        canvas.restore()
        canvas.drawText(GameMode.getModeText(), width / 2 - textWidth / 2, cw * 15, paint)
        canvas.translate(0f, cw * median)
        presenter.drawView()

        // 音声設定
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()
        soundPool = SoundPool.Builder()
            .setAudioAttributes(audioAttributes)
            .setMaxStreams(1)
            .build()
        soundOne = soundPool.load(context, R.raw.sound_japanese_chess, 1)
    }

    // 指した時の動作
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val c = (event.x / cw).toInt()
        val r = (event.y / ch - median).toInt()

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {}
            MotionEvent.ACTION_UP -> {
                presenter.onTouchEvent(c, r)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {}
            MotionEvent.ACTION_CANCEL -> {}
        }

        return true
    }

    // 将棋盤描画
    override fun drawBoard() {
        // 盤面セット
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.free_grain_sub)
        val rect1 = Rect(0, ch.toInt(), width, bh.toInt() + cw.toInt())
        val rect2 = Rect(0, ch.toInt(), bw.toInt() + bw.toInt(), bh.toInt() + cw.toInt())
        canvas.drawBitmap(bmp, rect1, rect2, paint)
        // 駒台セット
        paint.color = Color.rgb(251, 171, 83)
        canvas.drawRect(cw * 2, bw + cw, bw, bh + cw * 2.toFloat(), paint)
        canvas.drawRect(0f, 0f, bw - cw * 2.toFloat(), cw, paint)
        // 罫線セット
        paint.color = Color.rgb(40, 40, 40)
        paint.strokeWidth = 2f
        for (i in 0 until 9) canvas.drawLine(cw * (i + 1).toFloat(), cw, cw * (i + 1).toFloat(), bh + cw, paint)
        for (i in 0 until 9) canvas.drawLine(0f, ch * (i + 1), bw, ch * (i + 1), paint)
    }

    // 後手の駒描画
    override fun drawWhitePiece(name: String, i: Int, j: Int) {
        paint.textSize = cw / 2
        canvas.save()
        canvas.rotate(180f, (cw * i) + cw / 2, ch * 2 + (ch * j) - cw / 2)
        canvas.drawText(name, (cw * i) + cw / 5, ch * 2 + (ch * j) - cw / 4, paint)
        canvas.restore()
    }

    // 先手の駒描画
    override fun drawBlackPiece(name: String, i: Int, j: Int) {
        paint.textSize = cw / 2
        canvas.drawText(name, (cw * i) + cw / 5, ch * 2 + (ch * j) - cw / 4, paint)
    }

    // 先手の持ち駒描画
    override fun drawHoldPieceBlack(nameJP: String, stock: Int, count: Int) {
        paint.textSize = cw / 2
        canvas.drawText(nameJP, (cw * (count + 2)) + cw / 5, ch * 2 + (ch * 9) - cw / 4, paint)
        paint.textSize = cw / 5
        canvas.drawText(stock.toString(), (cw * (count + 2)) + cw * 3 / 4, ch * 2 + (ch * 9) - cw / 8, paint)
    }

    // 後手の持ち駒描画
    override fun drawHoldPieceWhite(nameJP: String, stock: Int, count: Int) {
        canvas.save()
        canvas.rotate(180f, cw * (7 - count), ch - cw / 2)
        paint.textSize = cw / 2
        canvas.drawText(nameJP, (cw * (7 - count)) + cw / 5, ch - cw / 4, paint)
        paint.textSize = cw / 5
        canvas.drawText(stock.toString(), (cw * (7 - count)) + cw * 3 / 4, ch - cw / 8, paint)
        canvas.restore()
    }

    // ヒント描画
    override fun drawHint(x: Int, y: Int) {
        paint.color = (Color.argb(200, 255, 255, 0))
        canvas.drawCircle((cw * x + cw / 2), (ch * (y + 1) + ch / 2), (bw / 9 * 0.46).toFloat(), paint)
        paint.color = Color.rgb(40, 40, 40)
    }

    // 成り判定ダイアログ
    override fun showDialog() {
        val alertBuilder = AlertDialog.Builder(context).setCancelable(false)
        alertBuilder.setMessage("成りますか？")
        alertBuilder.setPositiveButton("はい") { _, _ ->
            presenter.evolutionPiece(true)
            invalidate()
        }
        alertBuilder.setNegativeButton("いいえ") { _, _ ->
            presenter.evolutionPiece(false)
            invalidate()
        }
        alertBuilder.create().show()
    }

    // 終了ダイアログ表示
    override fun gameEnd(turn: Int) {
        activity.gameEnd(turn)
        val winTurn = if (turn == 1) 2 else 1
        activity.gameEndEmit(winTurn)
    }

    // 駒音再生
    override fun playbackEffect() {
        // play(ロードしたID, 左音量, 右音量, 優先度, ループ, 再生速度)
        soundPool.play(soundOne, 1.0f, 1.0f, 0, 0, 1.0f)
    }

    // 駒の動きを受信。受信側は判定を行わない　　viewの変更
    fun socketMove(oldX: Int, oldY: Int, newX: Int, newY: Int, evolution: Boolean) {
        presenter.socketMove(oldX, oldY, newX, newY, evolution)
        invalidate()
    }
    fun setTurn(turn: Int) {
        presenter.setTurn(turn)
    }
    override fun moveEmit(log: GameLog) {
        activity.moveEmit(log)
    }
}