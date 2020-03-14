package com.example.local_syogi.presentation.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.app.ActivityOptions
import android.util.Log
import android.view.MotionEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.view.account_information.SettingAccountFragment
import com.example.local_syogi.presentation.view.setting.SettingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gameButton:Button
    private lateinit var settingButton:Button
    private lateinit var title:TextView
    private lateinit var mainFragment: MainFragment
    private lateinit var selectFragment:SettingActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        gameButton = findViewById(R.id.game_button)
//        settingButton = findViewById(R.id.setting_button)
//        title = findViewById(R.id.title)
        mainFragment = MainFragment.newInstance(this)
        selectFragment = SettingActivity.newInstance(this)
        supportFragmentManager.beginTransaction()
            .add(R.id.base_frame,mainFragment,"MainFrag")
            .commit()
    }

//    fun game(v: View) {
//        closeActivity()
//
////        val i = Intent(this, SettingActivity::class.java)
////        startActivity(i)
////        overridePendingTransition(R.animator.fade_in, R.animator.fade_out_slide)
//    }
//
//    fun setting(v:View){
//       // closeActivity()
//        val i = Intent(this, SettingAccountFragment::class.java)
//        startActivity(i)
//    }
//
//    private fun closeActivity(){
//        val fade_in_slide: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_out_slide) as Animation
//
//        settingButton.startAnimation(fade_in_slide)
//        gameButton.startAnimation(fade_in_slide)
//        settingButton.visibility = View.INVISIBLE
//        gameButton.visibility = View.INVISIBLE
//    }
//
//    override fun onResume() {
//        super.onResume()
//        val fade: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in) as Animation
//
//        settingButton.startAnimation(fade)
//        gameButton.startAnimation(fade)
//
//        settingButton.visibility = View.VISIBLE
//        gameButton.visibility = View.VISIBLE
//    }

    fun gameSet(){
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                0,
                R.animator.fade_out
            )
            .replace(R.id.base_frame, selectFragment,"Game_Select")
            .commit()

    }

    fun backFragment(){
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                0,
                R.animator.fade_out
            )
            .replace(R.id.base_frame, mainFragment,"MainFrag")
            .commit()
    }

    var x:Int = 0
    var y:Int = 0
    var x2:Int = 0
    var y2:Int = 0
        override fun onTouchEvent(event: MotionEvent) :Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                x = event.x.toInt()
                y = event.y.toInt()
            }
            MotionEvent.ACTION_UP -> {
                x2 = event.x.toInt()
                y2 = event.y.toInt()
                if (supportFragmentManager.findFragmentByTag("MainFrag") != null && supportFragmentManager.findFragmentByTag("MainFrag")!!.isVisible) {
                    Log.d("Main","一致；home")
                }else if (supportFragmentManager.findFragmentByTag("Game_Select") != null && supportFragmentManager.findFragmentByTag("Game_Select")!!.isVisible) {
                    Log.d("Main","一致；seting"+ supportFragmentManager.findFragmentByTag("Game_Select")!!)
                    selectFragment.onTouchEvent(x,y,x2,y2)
                }else{
                    Log.d("Main","不一致；")
                }
//                if(x <= 400) {
//                    if (x2 - x < -10) {
//                        if(y in 800..1200){
//                            closeActivity()
//                        }else {
//                            flipCard(1)
//                        }
//                    } else if(10 < x2 - x) {
//                        flipCard(2)
//                    }
//                }
            }
        }

        return true
    }

    //戻るボタン
    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentByTag("Game_Select") != null && supportFragmentManager.findFragmentByTag("Game_Select")!!.isVisible) {
            selectFragment.onBackPressed()
        }else{
            super.onBackPressed()
        }

    }
}
