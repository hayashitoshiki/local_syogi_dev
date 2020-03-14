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
import com.example.local_syogi.R
import com.example.local_syogi.presentation.view.MainActivity
import com.example.local_syogi.presentation.view.game.GameRateActivity
import com.example.local_syogi.syogibase.presentation.view.GameActivity
import com.example.local_syogi.util.OnBackPressedListener


class SettingActivity  : Fragment(),OnBackPressedListener {


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
                    mode,
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
        val fadeUp: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_up_new) as Animation

        tabFrame.startAnimation(fade)
        tabFrame.visibility = View.VISIBLE
        title.startAnimation(fadeUp)
        title.visibility = View.VISIBLE
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
        val intent =
            if (mode == FREE) {
                Intent(context, GameActivity::class.java)
            }else{
                Intent(context, GameRateActivity::class.java)
            }
        startActivity(intent)
        closeActivity()
    }

    //activity終了
    private fun closeActivity(){
        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_slide) as Animation
        val fadeSpeed: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_speed) as Animation
        val fadeUp: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_up) as Animation

        tabFrame.startAnimation(fade)
        tabFrame.visibility = View.INVISIBLE
        modeFrame.startAnimation(fade)
        modeFrame.visibility = View.INVISIBLE
        title.startAnimation(fadeUp)
        title.visibility = View.INVISIBLE
        parentActivity.backFragment()
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

    companion object {
        const val FREE = 1
        const val RATE = 2
        private lateinit var parentActivity: MainActivity

        @JvmStatic
        fun newInstance(activity: MainActivity): SettingActivity {
            val fragment = SettingActivity()
            parentActivity = activity
            return fragment
        }
    }
}