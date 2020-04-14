package com.example.local_syogi.presentation.view.game

import android.app.AlertDialog
import android.content.Context
import android.graphics.*
import android.media.AudioAttributes
import android.media.SoundPool
import android.view.MotionEvent
import android.view.View
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.game.GameViewRateContact
import com.example.local_syogi.syogibase.data.game.GameLog
import com.example.local_syogi.syogibase.data.game.GameMode
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class GameRateView(private val activity: GameRateActivity, context: Context, width: Int, height: Int) : View(context), GameViewRateContact.View,
    KoinComponent {

    val presenter: GameViewRateContact.Presenter by inject { parametersOf(this) }
    private lateinit var canvas: Canvas
    private val paint: Paint = Paint()

    // 画像定義
    private val kingBmp = BitmapFactory.decodeResource(resources, R.drawable.syougi_king)
    private val kinBmp = BitmapFactory.decodeResource(resources, R.drawable.syougi_kin)
    private val ginBmp = BitmapFactory.decodeResource(resources, R.drawable.syougi_gin)
    private val nariginBmp = BitmapFactory.decodeResource(resources, R.drawable.syougi_narigin)
    private val keiBmp = BitmapFactory.decodeResource(resources, R.drawable.syougi_kei)
    private val narikeiBmp = BitmapFactory.decodeResource(resources, R.drawable.syougi_narikei)
    private val kyoBmp = BitmapFactory.decodeResource(resources, R.drawable.syougi_kyo)
    private val narikyoBmp = BitmapFactory.decodeResource(resources, R.drawable.syougi_narikyo)
    private val hisyaBmp = BitmapFactory.decodeResource(resources, R.drawable.syougi_hisya)
    private val ryuBmp = BitmapFactory.decodeResource(resources, R.drawable.syougi_ryu)
    private val kakuBmp = BitmapFactory.decodeResource(resources, R.drawable.syougi_kaku)
    private val umaBmp = BitmapFactory.decodeResource(resources, R.drawable.syougi_uma)
    private val fuBmp = BitmapFactory.decodeResource(resources, R.drawable.syougi_fu)
    private val tokinBmp = BitmapFactory.decodeResource(resources, R.drawable.syougi_to)
    private val rect1 = Rect(0, 0, kingBmp.width, kingBmp.height)

    private val bw: Float = if (width < height) {
        width.toFloat()
    } else {
        height.toFloat()
    } // 将棋盤の幅
    private val bh: Float = if (width < height) {
        width.toFloat()
    } else {
        height.toFloat()
    } // 将棋盤の高さ
    private var cw: Float = bw / 9 // １マスの幅
    private var ch: Float = bh / 9 // １マスの高さ
    private val median = 3 // 盤の位置　中央値：３ 範囲：０～６

    private lateinit var soundPool: SoundPool
    private var soundOne = 0

    // onCreat
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
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
        val rect1 = Rect(0, 0, bw.toInt(), bh.toInt())
        val rect2 = Rect(0, ch.toInt(), bw.toInt() + bw.toInt(), bh.toInt() + ch.toInt())
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

    // 駒の名前→画像へ変換
    private fun changeImageByPiece(name: String): Bitmap {
        return when (name) {
            "歩" -> fuBmp
            "と" -> tokinBmp
            "香" -> kyoBmp
            "杏" -> narikyoBmp
            "桂" -> keiBmp
            "圭" -> narikeiBmp
            "銀" -> ginBmp
            "全" -> nariginBmp
            "金" -> kinBmp
            "王" -> kingBmp
            "角" -> kakuBmp
            "馬" -> umaBmp
            "飛" -> hisyaBmp
            "龍" -> ryuBmp
            else -> fuBmp
        }
    }

    // 先手の駒描画
    override fun drawBlackPiece(name: String, i: Int, j: Int) {
        val rect2 = Rect((cw * i + cw / 8).toInt(), (ch + (ch * j) + cw / 10).toInt(), (cw * (i + 1) - cw / 8).toInt(), (ch + (ch * (j + 1)) - cw / 10).toInt())
        canvas.drawBitmap(changeImageByPiece(name), rect1, rect2, paint)
    }

    // 後手の駒描画
    override fun drawWhitePiece(name: String, i: Int, j: Int) {
        val rect2 = Rect((cw * i + cw / 8).toInt(), (ch + (ch * j) + cw / 10).toInt(), (cw * (i + 1) - cw / 8).toInt(), (ch + (ch * (j + 1)) - cw / 10).toInt())
        canvas.save()
        canvas.rotate(180f, (cw * i) + cw / 2, ch * 2 + (ch * j) - cw / 2)
        canvas.drawBitmap(changeImageByPiece(name), rect1, rect2, paint)
        canvas.restore()
    }

    // 先手の持ち駒描画
    override fun drawHoldPieceBlack(nameJP: String, stock: Int, count: Int) {
        val rect2 = Rect((cw * (count + 2) + cw / 8).toInt(), (ch + (ch * 9) + cw / 10).toInt(), (cw * (count + 3) - cw / 8).toInt(), (ch + (ch * 10) - cw / 10).toInt())
        canvas.drawBitmap(changeImageByPiece(nameJP), rect1, rect2, paint)
        paint.textSize = cw / 5
        canvas.drawText(stock.toString(), (cw * (count + 2)) + cw * 3 / 4, ch * 2 + (ch * 9) - cw / 8, paint)
    }

    // 後手の持ち駒描画
    override fun drawHoldPieceWhite(nameJP: String, stock: Int, count: Int) {
        val rect2 = Rect((cw * (7 - count) + cw / 8).toInt(), (0 + cw / 10).toInt(), (cw * (8 - count) - cw / 8).toInt(), (ch - cw / 10).toInt())
        paint.textSize = cw / 5
        canvas.save()
        canvas.rotate(180f, cw * (7 - count), ch - cw / 2)
        canvas.drawBitmap(changeImageByPiece(nameJP), rect1, rect2, paint)
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

    // 手番変更
    fun setTurn(turn: Int) {
        presenter.setTurn(turn)
    }

    // 指した手を送信
    override fun moveEmit(log: GameLog) {
        activity.moveEmit(log)
    }

    // 対局ログを返す
    fun getLog(winner: Int): MutableList<GameLog> {
        val log = presenter.getLog(winner)
        return log
    }
}