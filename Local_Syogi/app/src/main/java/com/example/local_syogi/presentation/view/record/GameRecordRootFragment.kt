package com.example.local_syogi.presentation.view.record

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Toast
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.GameRecordRootContact
import com.example.local_syogi.presentation.view.MainActivity
import com.example.local_syogi.presentation.view.account.AuthenticationBaseFragment
import com.example.local_syogi.presentation.view.account.NotLoginFragment
import com.example.local_syogi.presentation.view.game.GamePlayBackFragment
import com.example.local_syogi.syogibase.data.game.GameLog
import com.example.local_syogi.util.OnBackPressedListener

import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GameRecordRootFragment : Fragment(), GameRecordRootContact.View, OnBackPressedListener {

    private val presenter: GameRecordRootContact.Presenter by inject { parametersOf(this) }
    lateinit var authFragment: AuthenticationBaseFragment
    private lateinit var accountTab:GameRecordCardFragment
    private lateinit var tabFragment:FrameLayout
    private lateinit var mainFrame:FrameLayout
    private lateinit var main : MainActivity

    private var tab = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_record_root, container, false)
        main = activity as MainActivity
        authFragment = AuthenticationBaseFragment.newInstance2(presenter)
        accountTab = GameRecordCardFragment.newInstance(presenter)
        tabFragment = view.findViewById(R.id.tab)
        mainFrame = view.findViewById(R.id.fragment)

        if(isAdded) {
            childFragmentManager
                .beginTransaction()
                .add(R.id.tab, accountTab)
                .commit()
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_slide_new) as Animation
        val fadeDelay: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_delay) as Animation

        tabFragment.startAnimation(fade)
        tabFragment.visibility = View.VISIBLE
        mainFrame.startAnimation(fadeDelay)
        mainFrame.visibility = View.VISIBLE
    }




    fun onTouchEvent(x:Int, y:Int, x2:Int, y2: Int) {
        if(x <= 400) {
            if (x2 - x < -10) {
                if(y in 800..1200){
                    closeActivity()
                    val main = activity as MainActivity
                    main.backFragment()
                }else {
                    //flipCard(1)
                }
            } else if(10 < x2 - x) {
                //flipCard(2)
            }
        }
    }

    //初期状態でログイン画面を表示する
    override fun setLoginViewFirst(){
        childFragmentManager
            .beginTransaction()
            .add(R.id.fragment, NotLoginFragment())
            .commit()
    }
    //棋譜を表示する
    fun setRePlayView(log:MutableList<GameLog>){
        childFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fade_in_slide_from_right,
                0,
                0,
                R.anim.fade_out_slide_to_right
            )
            .replace(R.id.rePlayFragment, GameRePlayFragment(log))
            .addToBackStack(null)
            .commit()
    }
    //棋譜表示画面を閉じる
    fun endRePlay(){
        childFragmentManager.popBackStack()
    }

    //ログイン画面を表示する
    override fun setLoginView() {
        authFragment.setLoginView()
    }

    //ログイン後(設定)画面を表示する
    override fun setInformationView() {
        authFragment.setInformationView()
    }

    //エラー表示
    override fun showErrorEmailPassword(){
        Toast.makeText(context, "EmailとPasswordを入力してください", Toast.LENGTH_LONG).show()
    }
    //エラー表示
    override fun showErrorToast() {
        Toast.makeText(context, "通信環境の良いところでお試しください", Toast.LENGTH_LONG).show()
    }
    //ログアウトトースト表示
    override fun signOut(){
        Toast.makeText(context, "ログアウト", Toast.LENGTH_LONG).show()
        setLoginView()
    }

    //activity終了
    private fun closeActivity(){
        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_slide) as Animation
        val fadeOut: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out) as Animation

        tabFragment.startAnimation(fade)
        tabFragment.visibility = View.INVISIBLE
        mainFrame.startAnimation(fadeOut)
        mainFrame.visibility = View.INVISIBLE
        //val act = activity as MainActivity
        //main.backFragment()
    }

    fun changeMode(fragment: Fragment,tab:Int){
        childFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fade_in_slide,
                R.anim.fade_out_slide
            )
            .replace(R.id.fragment, fragment)
            .commit()
        this.tab = tab
    }

    //BackKey
    override fun onBackPressed() {
        closeActivity()
    }

    companion object {

        @JvmStatic
        fun newInstance(): GameRecordRootFragment {
            val fragment = GameRecordRootFragment()
            return fragment
        }
    }

}