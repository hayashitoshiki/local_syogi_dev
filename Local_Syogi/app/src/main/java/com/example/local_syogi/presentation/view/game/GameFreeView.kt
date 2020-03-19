package com.example.local_syogi.presentation.view.game

import android.content.Context
import android.graphics.*
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.GameViewRateContact
import com.example.local_syogi.presentation.presenter.GameLogicFreePresenter
import com.example.local_syogi.syogibase.data.BoardRepositoryImp
import com.example.local_syogi.syogibase.data.local.GameLog
import com.example.local_syogi.syogibase.data.local.GameMode
import com.example.local_syogi.syogibase.domain.SyogiLogicUseCaseImp
import com.example.local_syogi.syogibase.presentation.contact.GameViewContact
import com.example.local_syogi.syogibase.presentation.view.GameActivity
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class GameFreeView(context: Context, width:Int, height:Int,val log:MutableList<GameLog>): View(context), GameViewRateContact.View,
    KoinComponent {

   // private val presenter:GameViewContact.Presenter by inject{ parametersOf(this) }
    private val presenter: GameLogicFreePresenter =
       GameLogicFreePresenter(
           this as GameViewRateContact.View,
           SyogiLogicUseCaseImp(BoardRepositoryImp())
       )

    private lateinit var canvas: Canvas
    private val paint: Paint = Paint()

    private var count = 0

    //画像定義
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

    private var bw:Float = if(width < height){
        width.toFloat()
    }else{
        height.toFloat()
    }//将棋盤の幅
    private var bh:Float =  if(width < height){
        width.toFloat()
    }else{
        height.toFloat()
    }//将棋盤の高さ
    private var cw:Float = bw/9//１マスの幅
    private var ch:Float = bh/9//１マスの高さ
    private val median = 3 //盤の位置　中央値：３ 範囲：０～６

    private lateinit var soundPool: SoundPool
    private var soundOne = 0
    //onCreat
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.textSize = cw
        bw = width.toFloat()//将棋盤の幅
        bh = width.toFloat()//将棋盤の高さ
        cw = bw/9//１マスの幅
        ch = bh/9
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

    //指した時の動作
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val c = (event.x / cw).toInt()
        val r = (event.y / ch - median).toInt()

        when (event.action) {
            MotionEvent.ACTION_DOWN ->{}
            MotionEvent.ACTION_UP ->{
//                presenter.onTouchEvent(c,r)
               // invalidate()
                //TODO　後で絶対修正！！！
                if(c in 4..8){
                    goMove()
                }else if(c in 0..4){
                    backMove()
                }
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {}
            MotionEvent.ACTION_CANCEL -> {}
        }
        return true
    }

    //将棋盤描画
    override fun drawBoard(){
        //盤面セット
        val bmp = BitmapFactory.decodeResource(resources, R.drawable.free_grain_sub)
        val rect1 = Rect(0, 0, bw.toInt(), bh.toInt())
        val rect2 = Rect(0, ch.toInt(), bw.toInt() + bw.toInt(), bh.toInt()+ch.toInt())
        canvas.drawBitmap(bmp, rect1, rect2, paint)
        //駒台セット
        paint.color = Color.rgb(251, 171, 83)
        canvas.drawRect(cw * 2, bw + cw, bw, bh + cw * 2.toFloat(), paint)
        canvas.drawRect(0f, 0f, bw - cw * 2.toFloat(), cw, paint)
        //罫線セット
        paint.color = Color.rgb(40, 40, 40)
        paint.strokeWidth = 2f
        for (i in 0 until 9) canvas.drawLine(cw * (i + 1).toFloat(), cw, cw * (i + 1).toFloat(), bh + cw, paint)
        for (i in 0 until 9) canvas.drawLine(0f, ch * (i + 1), bw, ch * (i + 1), paint)
    }

    //駒の名前→画像へ変換
    private fun changeImageByPiece(name:String): Bitmap {
        return when(name){
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

    //先手の駒描画
    override fun drawBlackPiece(name:String, i:Int, j:Int){
        val rect2 = Rect((cw*i + cw/8).toInt(), (ch+(ch*j) + cw/10).toInt(), (cw*(i+1) - cw/8).toInt(), (ch+(ch*(j+1)) - cw/10).toInt())
        canvas.drawBitmap(changeImageByPiece(name), rect1, rect2, paint)
    }

    //後手の駒描画
    override fun drawWhitePiece(name:String, i:Int, j:Int){
        val rect2 = Rect((cw*i + cw/8).toInt(), (ch+(ch*j) + cw/10).toInt(), (cw*(i+1) - cw/8).toInt(), (ch+(ch*(j+1)) - cw/10).toInt())
        canvas.save()
        canvas.rotate(180f, (cw * i) + cw /2, ch*2 + (ch * j) - cw / 2)
        canvas.drawBitmap(changeImageByPiece(name), rect1, rect2, paint)
        canvas.restore()
    }

    //先手の持ち駒描画
    override fun drawHoldPieceBlack(nameJP:String,stock:Int, count:Int){
        val rect2 = Rect((cw*(count+2) + cw/8).toInt(), (ch+(ch*9) + cw/10).toInt(), (cw*(count+3) - cw/8).toInt(), (ch+(ch*10) - cw/10).toInt())
        canvas.drawBitmap(changeImageByPiece(nameJP), rect1, rect2, paint)
        paint.textSize = cw / 5
        canvas.drawText(stock.toString(), (cw*(count+2))+cw*3/4, ch*2+(ch*9)-cw/8, paint)
    }

    //後手の持ち駒描画
    override fun drawHoldPieceWhite(nameJP:String, stock:Int, count:Int){
        val rect2 = Rect((cw*(7-count) + cw/8).toInt(), (0 + cw/10).toInt(), (cw*(8 -count) - cw/8).toInt(), (ch - cw/10).toInt())
        paint.textSize = cw / 5
        canvas.save()
        canvas.rotate(180f, cw*(7-count), ch-cw/2)
        canvas.drawBitmap(changeImageByPiece(nameJP), rect1, rect2, paint)
        canvas.drawText(stock.toString(), (cw*(7-count))+cw*3/4, ch-cw/8, paint)
        canvas.restore()
    }

    //駒の動きを受信。受信側は判定を行わない　　viewの変更
    fun goMove() {
        if(log.size > count) {
            presenter.socketMove(
                log[count].oldX,
                log[count].oldY,
                log[count].newX,
                log[count].newY,
                log[count].evolution)
            count++
        }
    }
    //一手戻す
    fun backMove(){
        if(log.size != 0 && count != 0) {
            presenter.setBackMove()
            count--
        }
    }
    //最初まで戻す
    fun backMoveFirst(){
        while(count != 0){
            backMove()
        }
        invalidate()
    }
    //最後まで動かす
    fun goMoveLast(){
        while(log.size > count){
            goMove()
        }
        invalidate()
    }
    override fun moveEmit(log: GameLog){}

    //ヒント描画
    override fun drawHint(i:Int,j:Int){}
    //成るか判断するダイアログ生成
    override fun showDialog(){}
    //対局終了モーダル生成
    override fun gameEnd(turn:Int){}
    //効果音を鳴らす
    override fun playbackEffect(){

    }


}