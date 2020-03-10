package com.example.local_syogi.presentation.view.game

import androidx.fragment.app.Fragment

class GameFragment  : Fragment() {

//     override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): view? {
//        val view = inflater.inflate(R.layout.fragment_chaos_syogi, container, false)
//
//        return view
//    }
//    //投了ボタン
//    fun surrenderBlack(v: view){
//        surrender(2)
//    }
//    fun surrenderWhite(v: view){
//        surrender(1)
//    }
//    private fun surrender(turn:Int){
//        AlertDialog.Builder(this)
//            .setMessage("投了しますか？")
//            .setPositiveButton("はい") { _, _ ->
//                gameEnd(turn)
//            }
//            .setNegativeButton("いいえ", null)
//            .show()
//    }
//
//    //ゲーム終了後画面
//    fun gameEnd(turn:Int){
//        val button: Button = findViewById(R.id.surrender_white)
//        val button2: Button = findViewById(R.id.surrender_black)
//        val viewGroup = this.findViewById(R.id.frame2) as FrameLayout
//        val endView: view = layoutInflater.inflate(R.layout.modal_game_end, viewGroup)
//        val winLoseView: view = WinLoseModal(this, turn)
//        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
//
//        //socket.emit("game-end","1")
//        view.loseButton()
//        button.visibility = view.INVISIBLE
//        button2.visibility = view.INVISIBLE
//        frame!!.addView(winLoseView, 1)
//        endView.startAnimation(animation)
//    }
//
//    //終了ボダン
//    fun end(v: view) {
//        finish()
//    }
//
//    //もう一度ボタン
//    fun restart(v: view) {
//        finish()
//        val intent = Intent(this, MattingActivity()::class.java)
//        startActivity(intent)
//
//    }
//
//    //戻るボタンの無効化
//    override fun onBackPressed() {}
//
//
//
//
//    override fun onDestroy() {
//        super.onDestroy()
////        socket.disconnect()
////        socket.off(Socket.EVENT_CONNECT,connection)
////        socket.off(Socket.EVENT_CONNECT_ERROR, onConnectError)
////        socket.off("message",message)
////        socket.off("joined-new",onJoinRoomClient)
////        socket.off("onGameEnd",onGameEnd)
////        socket.off("move",move)
//        view.activityDestroy()
//        Log.d("MainActivity","socketオフ")
//    }
//    fun onGameView(){
//        frame!!.addView(view, 0)
//    }

}