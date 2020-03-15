package com.example.local_syogi.presentation.view.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.local_syogi.syogibase.data.local.GameMode
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.Animation
import androidx.constraintlayout.widget.ConstraintLayout
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.SettingRootContact
import com.example.local_syogi.presentation.contact.UsuallySyogiContact
import com.example.local_syogi.presentation.view.MainActivity
import com.example.local_syogi.presentation.view.game.GameRateActivity
import com.example.local_syogi.syogibase.presentation.view.GameActivity
import com.example.local_syogi.util.OnBackPressedListener
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class SettingRootFragment  : Fragment(), SettingRootContact.View,OnBackPressedListener {

    private val presenter: SettingRootContact.Presenter by inject { parametersOf(this) }


    private lateinit var view2:ConstraintLayout
    private lateinit var tabFrame:FrameLayout
    private lateinit var modeFrame:FrameLayout
    private lateinit var title:TextView
    private var tab = -1
    private var mode = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_game_setting, container, false)
        modeFrame = view.findViewById(R.id.mode_frame)
        title = view.findViewById(R.id.title)
        tabFrame = view.findViewById(R.id.tab)

        childFragmentManager.beginTransaction()
            .add(R.id.tab,
                SelectNormalFragment.newInstance(
                    FREE,
                    tab
                )
            )
            .commit()

        view2 = view.findViewById(R.id.test)
        return view
    }

    override fun onStart() {
        super.onStart()
        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_slide_new) as Animation
        val fadeDelay: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_slide_new_delay) as Animation
        val fadeUp: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_up_new) as Animation

        tabFrame.startAnimation(fade)
        tabFrame.visibility = View.VISIBLE
        title.startAnimation(fadeUp)
        title.visibility = View.VISIBLE
        modeFrame.startAnimation(fadeDelay)
        modeFrame.visibility = View.VISIBLE
    }

    /* タブのボタンを押下
       選択していないボタンは白色にして
       選択しているボタンは指定職にする
       また、fragmentを入れ替える */
    fun changeMode(fragment: Fragment,tab:Int){
        childFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fade_in_slide,
                R.anim.fade_out_slide
            )
            .replace(R.id.mode_frame, fragment)
            .commit()
        this.tab = tab
    }

    //タッチイベント
    fun onTouchEvent(x:Int, y:Int, x2:Int, y2:Int) {
        if(x <= 400) {
            if (x2 - x < -10) {
                if(y in 800..1200){
                    closeActivity()
                    val main = activity as MainActivity
                    main.backFragment()
                }else {
                    flipCard(1)
                }
            } else if(10 < x2 - x) {
                flipCard(2)
            }
        }
    }

    //タブ切り替えモーション
    private fun flipCard(roll:Int) {
        mode = if(mode == FREE){
            RATE
        }else{
            FREE
        }
        if(roll == 1) {
            childFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.card_flip_right_in,
                    R.anim.card_flip_right_out
                )
                .replace(R.id.tab,
                    SelectNormalFragment.newInstance(
                        mode,
                        tab
                    )
                )
                .commit()
        }else {
            childFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.card_flip_left_in,
                    R.anim.card_flip_left_out
                )
                .replace(R.id.tab,
                    SelectNormalFragment.newInstance(
                        mode,
                        tab
                    )
                )
                .commit()
        }
    }

    //対局開始
    fun fadeOut(){
        if (mode == FREE) {
            val intent = Intent(context, GameActivity::class.java)
            startActivity(intent)
        }else if(presenter.isAuth()){
            val intent = Intent(context, GameRateActivity::class.java)
            startActivity(intent)
        }else {
            showAuthToast()
        }
    }

    //activity終了
    private fun closeActivity(){
        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_slide_delay) as Animation
        val fadeSpeed: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_speed) as Animation
        val fadeUp: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_up) as Animation

        tabFrame.startAnimation(fade)
        tabFrame.visibility = View.INVISIBLE
        modeFrame.startAnimation(fadeSpeed)
        modeFrame.visibility = View.INVISIBLE
        title.startAnimation(fadeUp)
        title.visibility = View.INVISIBLE
    }

    override fun onResume(){
        super.onResume()
        GameMode.reset()
        view2.visibility = View.VISIBLE
    }

    //BackKey
    override fun onBackPressed() {
        closeActivity()
    }

    //エラー表示
    private fun showAuthToast() {
        Toast.makeText(context, "ログインしてください", Toast.LENGTH_LONG).show()
    }

    companion object {
        const val FREE = 1
        const val RATE = 2

        @JvmStatic
        fun newInstance(): SettingRootFragment {
            val fragment = SettingRootFragment()
            return fragment
        }
    }
}