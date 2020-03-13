package com.example.local_syogi.presentation.view.account_information

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.SettingAccountContact

import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/*
 *  DI用クラスに以下の１行を追加してください
 */


class SettingAccountFragment : AppCompatActivity(), SettingAccountContact.View {

    private val presenter: SettingAccountContact.Presenter by inject { parametersOf(this) }
    private lateinit var rateCard:RateCardFragment
    private lateinit var nomalCard:NomalCardFragment

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
        setContentView(R.layout.fragment_setting_account)
        rateCard = RateCardFragment.newInstance(presenter)
        nomalCard = NomalCardFragment()
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment, rateCard)
            .commit()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.tab, nomalCard)
            .commit()
    }

    override fun onStart() {
        super.onStart()
       // presenter.onStart()
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
                        if(y in 800..1200){
                            Log.d("Setting","閉じる")
                            closeActivity()
                        }else {
                            //flipCard(1)
                        }
                    } else if(10 < x2 - x) {
                        //flipCard(2)
                    }
                }
            }
        }
        return true
    }

    //タブ切り替えモーション
    private fun flipCard(roll:Int) {

        val fragment =
            if(roll == 1) {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.animator.card_flip_right_in,
                        R.animator.card_flip_right_out)
            }else {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.animator.card_flip_left_in,
                        R.animator.card_flip_left_out)
            }

        if(mode == FREE){
             mode = RATE
            fragment.replace(R.id.fragment, rateCard).commit()
        }else{
             mode = FREE
            fragment.replace(R.id.fragment, nomalCard).commit()
        }
    }

    //ログイン画面を表示する
    override fun setLoginView() {
        rateCard.setLoginView()
    }

    //ログイン後(設定)画面を表示する
    override fun setInformationView() {
        rateCard.setInformationView()
    }

    //エラー表示
    override fun showErrorEmailPassword(){
        Toast.makeText(applicationContext, "EmailとPasswordを入力してください", Toast.LENGTH_LONG).show()
    }
    //エラー表示
    override fun showErrorToast() {
        Toast.makeText(applicationContext, "通信環境の良いところでお試しください", Toast.LENGTH_LONG).show()
    }
    //ログアウトトースト表示
    override fun signOut(){
        Toast.makeText(applicationContext, "ログアウト", Toast.LENGTH_LONG).show()
        setLoginView()
    }

    //activity終了
    private fun closeActivity(){
        val fade: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_out) as Animation
        val frame: FrameLayout = findViewById(R.id.tab)
        frame.startAnimation(fade)
        frame.visibility = View.INVISIBLE
        finish()
    }
}