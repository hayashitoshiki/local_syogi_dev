package com.example.local_syogi.presentation.view.account_information

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.SettingAccountContact
import com.example.local_syogi.presentation.view.MainActivity
import com.example.local_syogi.util.OnBackPressedListener

import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/*
 *  DI用クラスに以下の１行を追加してください
 */


class SettingAccountFragment : Fragment(), SettingAccountContact.View,OnBackPressedListener {

    private val presenter: SettingAccountContact.Presenter by inject { parametersOf(this) }
    private lateinit var rateCard:RateCardFragment
    private lateinit var nomalCard:NomalCardFragment
    private lateinit var tabFragment:FrameLayout
    private lateinit var mainFrame:FrameLayout
    private lateinit var main :MainActivity

    private var mode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_setting_account, container, false)
        main = activity as MainActivity
        rateCard = RateCardFragment.newInstance(presenter)
        nomalCard = NomalCardFragment()
        tabFragment = view.findViewById(R.id.tab)
        mainFrame = view.findViewById(R.id.fragment)

        childFragmentManager
            .beginTransaction()
            .add(R.id.fragment, rateCard)
            .commit()
        childFragmentManager
            .beginTransaction()
            .replace(R.id.tab, nomalCard)
            .commit()
        return view
    }

    override fun onStart() {
        super.onStart()
        // presenter.onStart()
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

    //タブ切り替えモーション
    private fun flipCard(roll:Int) {

        val fragment =
            if(roll == 1) {
                childFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.card_flip_right_in,
                        R.anim.card_flip_right_out)
            }else {
                childFragmentManager.beginTransaction()
                    .setCustomAnimations(
                        R.anim.card_flip_left_in,
                        R.anim.card_flip_left_out)
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

    //BackKey
    override fun onBackPressed() {
        closeActivity()
    }

    companion object {
        const val FREE = 1
        const val RATE = 2

        @JvmStatic
        fun newInstance(): SettingAccountFragment {
            val fragment = SettingAccountFragment()
            return fragment
        }
    }
}