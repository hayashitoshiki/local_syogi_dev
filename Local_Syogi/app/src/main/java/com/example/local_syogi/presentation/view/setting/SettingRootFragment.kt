package com.example.local_syogi.presentation.view.setting

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.setting.SettingRootContact
import com.example.local_syogi.presentation.view.MainActivity
import com.example.local_syogi.presentation.view.game.GameRateActivity
import com.example.local_syogi.syogibase.data.entity.game.GameMode
import com.example.local_syogi.syogibase.presentation.view.GameActivity
import com.example.local_syogi.util.OnBackPressedListener
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SettingRootFragment : Fragment(), SettingRootContact.View, OnBackPressedListener {

    private val presenter: SettingRootContact.Presenter by inject { parametersOf(this) }

    private lateinit var adapter: CustomFragmentPagerAdapter
    private lateinit var pager: CustomViewPager
    private lateinit var view2: ConstraintLayout
    private lateinit var tabFrame: FrameLayout
    private lateinit var modeFrame: FrameLayout
    private var syogiBaseFragment: SettingSyogiBaseFragment? = null
    private lateinit var title: TextView
    private var tab = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_game_setting, container, false)
        modeFrame = view.findViewById(R.id.mode_frame)
        title = view.findViewById(R.id.title)
        tabFrame = view.findViewById(R.id.tab)
        presenter.initMode()

        childFragmentManager.beginTransaction()
            .add(
                R.id.tab,
                SettingCardBaseFragment.newInstance(
                    FREE,
                    tab
                )
            )
            .commit()

        view2 = view.findViewById(R.id.test)

        adapter = CustomFragmentPagerAdapter(childFragmentManager)
        pager = view.findViewById(R.id.pager)
        pager.adapter = adapter
        pager.setPagingEnabled(false)
        pager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> pager.setPagingEnabled(false)
                    else -> pager.setPagingEnabled(true)
                }
            }
        })
        return view
    }

    override fun onStart() {
        super.onStart()
        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_slide_new) as Animation
        val fadeDelay: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_slide_new_delay) as Animation
        val fadeIn: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_delay) as Animation
        val fadeInRight: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_slide_from_right_delay) as Animation

        tabFrame.startAnimation(fade)
        tabFrame.visibility = View.VISIBLE
        if (title.visibility != View.GONE) {
            title.startAnimation(fadeIn)
            title.visibility = View.VISIBLE
        }
        modeFrame.startAnimation(fadeDelay)
        modeFrame.visibility = View.VISIBLE
        pager.startAnimation(fadeInRight)
        pager.visibility = View.VISIBLE
    }

    /* タブのボタンを押下
       選択していないボタンは白色にして
       選択しているボタンは指定職にする
       また、fragmentを入れ替える */
    fun changeMode(fragment: Fragment, tab: Int) {
        if (title.visibility != View.GONE) {
            firstChoice()
        }
        syogiBaseFragment = fragment as SettingSyogiBaseFragment
        childFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fade_in_slide,
                R.anim.fade_out_slide
            )
            .replace(R.id.mode_frame, fragment)
            .commit()
        this.tab = tab
    }

    // タッチイベント
    fun onTouchEvent(x: Int, y: Int, x2: Int, y2: Int) {
        presenter.onTouchEvent(x, y, x2, y2)
    }

    // ホーム画面へ戻る
    override fun backHome() {
        closeActivity()
        val main = activity as MainActivity
        main.backFragment()
    }

    // 「モード選択」(タイトル)Viewを非表示にする
    override fun firstChoice() {
        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out) as Animation
        if (title.visibility != View.GONE) {
            title.startAnimation(fade)
            title.visibility = View.GONE
        }
    }

    // タブカード回転
    override fun flipCardRight(mode: Int) {
        childFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.card_flip_right_in,
                R.anim.card_flip_right_out
            )
            .replace(
                R.id.tab,
                SettingCardBaseFragment.newInstance(
                    mode,
                    tab
                )
            )
            .commit()
        if (syogiBaseFragment != null) {
            when (mode) {
                1 -> syogiBaseFragment!!.setOfflineMode()
                2 -> syogiBaseFragment!!.setOnlineMode()
            }
        }
    }

    // タブカード回転
    override fun flipCardLeft(mode: Int) {
        childFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.card_flip_left_in,
                R.anim.card_flip_left_out
            )
            .replace(
                R.id.tab,
                SettingCardBaseFragment.newInstance(
                    mode,
                    tab
                )
            )
            .commit()
        if (syogiBaseFragment != null) {
            when (mode) {
                1 -> syogiBaseFragment!!.setOfflineMode()
                2 -> syogiBaseFragment!!.setOnlineMode()
            }
        }
    }

    // 対局開始判定ロジックへ
    fun fadeOut() {
        presenter.startGame()
    }

    // 通常対戦画面へ遷移
    override fun startNomarGame() {
        val intent = Intent(context, GameActivity::class.java)
        startActivity(intent)
    }

    // 通信対戦画面へ遷移
    override fun startRateGame() {
        val intent = Intent(context, GameRateActivity::class.java)
        startActivity(intent)
    }

    // activity終了
    private fun closeActivity() {
        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_slide_delay) as Animation
        val fadeSpeed: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_speed) as Animation
        val fadeOut: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out) as Animation
        val fadeOutRight: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_slide_to_right_delay) as Animation

        tabFrame.startAnimation(fade)
        tabFrame.visibility = View.INVISIBLE
        modeFrame.startAnimation(fadeSpeed)
        modeFrame.visibility = View.INVISIBLE
        pager.startAnimation(fadeOutRight)
        pager.visibility = View.INVISIBLE
        if (title.visibility == View.VISIBLE) {
            title.startAnimation(fadeOut)
            title.visibility = View.INVISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        GameMode.reset()
        view2.visibility = View.VISIBLE
    }

    // BackKey
    override fun onBackPressed() {
        closeActivity()
    }

    // エラー表示
    override fun showAuthToast() {
        Toast.makeText(context, "ログインしてください", Toast.LENGTH_LONG).show()
    }

    companion object {
        const val FREE = 1

        @JvmStatic
        fun newInstance(): SettingRootFragment {
            val fragment = SettingRootFragment()
            return fragment
        }
    }
}