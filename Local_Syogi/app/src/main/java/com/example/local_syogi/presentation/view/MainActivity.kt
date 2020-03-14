package com.example.local_syogi.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import com.example.local_syogi.R
import com.example.local_syogi.presentation.view.account_information.SettingAccountFragment
import com.example.local_syogi.presentation.view.setting.SettingActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainFragment: MainFragment
    private lateinit var selectFragment:SettingActivity
    private lateinit var accountFragment:SettingAccountFragment

    var x:Int = 0
    var y:Int = 0
    var x2:Int = 0
    var y2:Int = 0

    companion object {
        const val HOME = "home"
        const val SELECTGAME = "setting"
        const val ACCOUNT = "account"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainFragment = MainFragment.newInstance()
        selectFragment = SettingActivity.newInstance()
        accountFragment = SettingAccountFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.base_frame,mainFragment,HOME)
            .commit()
    }

    //モード選択画面へ
    fun gameSet(){
        supportFragmentManager.beginTransaction()
            //.setReorderingAllowed(true)
            .setCustomAnimations(
                0,
                R.anim.fade_out_card,
                0,
                R.anim.fade_out_card
            )
            .replace(R.id.base_frame, selectFragment,SELECTGAME)
            .addToBackStack(null)
            .commit()
    }

    fun account(){
        supportFragmentManager.beginTransaction()
            //.setReorderingAllowed(true)
            .setCustomAnimations(
                0,
                R.anim.fade_out_card,
                0,
                R.anim.fade_out_card
            )
            .replace(R.id.base_frame, accountFragment, ACCOUNT)
            .addToBackStack(null)
            .commit()
    }

    //戻る
    fun backFragment(){
        supportFragmentManager.popBackStack()
    }

    //タッチイベント
    override fun onTouchEvent(event: MotionEvent) :Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                x = event.x.toInt()
                y = event.y.toInt()
            }
            MotionEvent.ACTION_UP -> {
                x2 = event.x.toInt()
                y2 = event.y.toInt()
                if (supportFragmentManager.findFragmentByTag(HOME) != null && supportFragmentManager.findFragmentByTag(HOME)!!.isVisible) {

                }else if (supportFragmentManager.findFragmentByTag(SELECTGAME) != null && supportFragmentManager.findFragmentByTag(SELECTGAME)!!.isVisible) {
                    selectFragment.onTouchEvent(x,y,x2,y2)

                }else if (supportFragmentManager.findFragmentByTag(ACCOUNT) != null && supportFragmentManager.findFragmentByTag(ACCOUNT)!!.isVisible){
                    accountFragment.onTouchEvent(x,y,x2,y2)
                }else{
                    Log.d("Main","不一致；エラー")
                }
            }
        }
        return true
    }

    //BackKeyイベント
    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentByTag(SELECTGAME) != null && supportFragmentManager.findFragmentByTag(SELECTGAME)!!.isVisible) {
            selectFragment.onBackPressed()
            backFragment()
        }else if (supportFragmentManager.findFragmentByTag(ACCOUNT) != null && supportFragmentManager.findFragmentByTag(ACCOUNT)!!.isVisible) {
            accountFragment.onBackPressed()
            backFragment()
        }else{
            super.onBackPressed()
        }

    }
}
