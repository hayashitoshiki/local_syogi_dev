package com.example.local_syogi.presentation.view.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.account.SettingAccountContact
import com.example.local_syogi.presentation.view.MainActivity
import com.example.local_syogi.syogibase.data.entity.game.GameMode
import com.example.local_syogi.util.OnBackPressedListener
import kotlinx.android.synthetic.main.fragment_setting_account.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AccountRootFragment : Fragment(), SettingAccountContact.View, OnBackPressedListener {

    private val presenter: SettingAccountContact.Presenter by inject { parametersOf(this) }
    private lateinit var authFragment: AuthenticationBaseFragment
    private lateinit var accountTab: AccountCardFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_setting_account, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        authFragment = AuthenticationBaseFragment.newInstance()
        accountTab = AccountCardFragment.newInstance(presenter)

        childFragmentManager
            .beginTransaction()
            .replace(R.id.tab, accountTab)
            .commit()

        presenter.onStart()
        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_slide_new) as Animation
        val fadeDelay: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_delay) as Animation

        tab.startAnimation(fade)
        tab.visibility = View.VISIBLE
        mainFrame.startAnimation(fadeDelay)
        mainFrame.visibility = View.VISIBLE
    }

    fun onTouchEvent(x: Int, y: Int, x2: Int, y2: Int) {
        if (x <= 400) {
            if (x2 - x < -10) {
                if (y in 800..1200) {
                    closeActivity()
                    val main = activity as MainActivity
                    main.backFragment()
                } else {
                    // flipCard(1)
                }
            } else if (10 < x2 - x) {
                // flipCard(2)
            }
        }
    }

    // 初期状態でログイン画面を表示する
    override fun setLoginViewFirst() {
        childFragmentManager
            .beginTransaction()
            .add(R.id.fragment, NotLoginFragment())
            .commit()
    }

    // ログイン画面を表示する
    override fun setLoginView() {
        authFragment.setLoginView()
    }

    // ログイン後(設定)画面を表示する
    override fun setInformationView() {
        authFragment.setInformationView()
    }

    // activity終了
    private fun closeActivity() {
        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_slide) as Animation
        val fadeOut: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out) as Animation

        tab.startAnimation(fade)
        tab.visibility = View.INVISIBLE
        mainFrame.startAnimation(fadeOut)
        mainFrame.visibility = View.INVISIBLE
    }

    // 各対戦成績表示
    fun changeMode(mode : Int) {
        val fragment = when {
            mode == 1000 -> authFragment
            presenter.isSession() -> { when (mode) {
                2000 -> AccountFollowFragment()
                0 -> ResultListFragment.newInstance("総合成績")
                GameMode.NORMAL -> ResultListFragment.newInstance(resources.getString(R.string.syogi))
                GameMode.BACKMOVE -> ResultListFragment.newInstance(resources.getString(R.string.annnan_syogi))
                GameMode.CHAOS -> ResultListFragment.newInstance(resources.getString(R.string.chaos_syogi))
                GameMode.TWOTIME -> ResultListFragment.newInstance(resources.getString(R.string.secound_syogi))
                GameMode.CHECKMATE -> ResultListFragment.newInstance(resources.getString(R.string.check_syogi))
                GameMode.LIMIT -> ResultListFragment.newInstance(resources.getString(R.string.piece_syogi))
                else -> null
            } }
            else -> NotLoginFragment()
        }

        fragment?.let {
            childFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.fade_in_slide,
                    R.anim.fade_out_slide
                )
                .replace(R.id.mainFrame, it)
                .commit()
        }
    }

    // BackKey
    override fun onBackPressed() {
        closeActivity()
    }

    companion object {

        @JvmStatic
        fun newInstance(): AccountRootFragment {
            return AccountRootFragment()
        }
    }
}