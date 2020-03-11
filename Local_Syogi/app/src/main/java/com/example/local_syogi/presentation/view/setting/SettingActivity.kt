package com.example.local_syogi.presentation.view.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.local_syogi.syogibase.data.local.GameMode
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.Animation
import androidx.constraintlayout.widget.ConstraintLayout
import android.content.Intent
import android.view.MotionEvent
import com.example.local_syogi.R
import com.example.local_syogi.presentation.view.game.GameRateActivity
import com.example.local_syogi.syogibase.presentation.view.GameActivity


class SettingActivity  : AppCompatActivity() {


    private lateinit var view:ConstraintLayout
    private var tab = -1
    private var mode = 1
    companion object {
        const val FREE = 1
        const val RATE = 2
    }

    var x:Int = 0
    var y:Int = 0
    var x2:Int = 0
    var y2:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_setting)

        supportFragmentManager.beginTransaction()
            .add(R.id.tab,
                SelectNormalFragment.newInstance(
                    mode,
                    tab
                )
            )
            .commit()
        view = findViewById(R.id.test)
    }

    /* タブのボタンを押下
       選択していないボタンは白色にして
       選択しているボタンは指定職にする
       また、fragmentを入れ替える */
    fun changeMode(fragment: Fragment,tab:Int){
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.animator.fade,
                R.animator.fade_out
            )
            .replace(R.id.mode_frame, fragment)
            .commit()
        this.tab = tab
    }

    override fun onTouchEvent(event: MotionEvent) :Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                x = event.x.toInt()
                y = event.y.toInt()
            }
            MotionEvent.ACTION_UP -> {
                x2 = event.x.toInt()
                y2 = event.y.toInt()
                if(x <= 400) {
                    if (x2 - x < -10) {
                        flipCard(1)
                    } else if(10 < x2 - x) {
                        flipCard(2)
                    }
                }
            }
        }

        return true
    }

    //タブ切り替えモーション
    private fun flipCard(roll:Int) {
        mode = if(mode == FREE){
            RATE
        }else{
            FREE
        }
        if(roll == 1) {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.animator.card_flip_right_in,
                    R.animator.card_flip_right_out
                )
                .replace(R.id.tab,
                    SelectNormalFragment.newInstance(
                        mode,
                        tab
                    )
                )
                .commit()
        }else {
            supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.animator.card_flip_left_in,
                    R.animator.card_flip_left_out
                )
                .replace(R.id.tab,
                    SelectNormalFragment.newInstance(
                        mode,
                        tab
                    )
                )
                .commit()
        }
        //もう一度現在のfragmentを表示する//modeを変えて、fadeで
    }

    //対局開始
    fun fadeOut(){
        val intent =
            if (mode == FREE) {
                Intent(this, GameActivity::class.java)
            }else{
                Intent(this, GameRateActivity::class.java)
            }
        startActivity(intent)
        val inAnimation = (AnimationUtils.loadAnimation(this, R.anim.fade) as Animation)
        view.startAnimation(inAnimation)
        view.setVisibility(View.INVISIBLE)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }

    override fun onResume(){
        super.onResume()
        GameMode.reset()
        view.setVisibility(View.VISIBLE)
    }


}