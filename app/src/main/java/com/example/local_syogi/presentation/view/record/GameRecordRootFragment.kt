package com.example.local_syogi.presentation.view.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.record.GameRecordRootContact
import com.example.local_syogi.presentation.view.MainActivity
import com.example.local_syogi.presentation.view.account.AuthenticationBaseFragment
import com.example.local_syogi.presentation.view.account.NotLoginFragment
import com.example.local_syogi.syogibase.data.entity.game.GameLog
import com.example.local_syogi.syogibase.data.entity.game.GameMode
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import com.example.local_syogi.util.OnBackPressedListener
import kotlinx.android.synthetic.main.fragment_game_record_root.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GameRecordRootFragment : Fragment(), GameRecordRootContact.View, OnBackPressedListener {

    private val presenter: GameRecordRootContact.Presenter by inject { parametersOf(this) }
    private lateinit var authFragment: AuthenticationBaseFragment
    private lateinit var accountTab: GameRecordCardFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_record_root, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.onStart()

        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_slide_new) as Animation
        val fadeDelay: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_in_delay) as Animation

        authFragment = AuthenticationBaseFragment.newInstance()
        accountTab = GameRecordCardFragment.newInstance()

        if (isAdded) {
            childFragmentManager
                .beginTransaction()
                .add(R.id.tab, accountTab)
                .commit()
        }

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
                    (activity as MainActivity).backFragment()
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
            .add(R.id.mainFrame, NotLoginFragment())
            .commit()
    }

    // 棋譜を表示する
    fun setRePlayView(log: MutableList<GameLog>, gameDetail: GameDetailSetitngModel) {
        childFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.fade_in_slide_from_right,
                0,
                0,
                R.anim.fade_out_slide_to_right
            )
            .replace(R.id.rePlayFragment, GameRePlayFragment(log, gameDetail))
            .addToBackStack(null)
            .commit()
    }

    // 棋譜表示画面を閉じる
    fun endRePlay() {
        childFragmentManager.popBackStack()
    }

    // ログイン画面を表示する
    override fun setLoginView() {
        authFragment.setLoginView()
    }

    // ログイン後(設定)画面を表示する
    override fun setInformationView() {
        authFragment.setInformationView()
    }

    // エラー表示
    override fun showErrorEmailPassword() {
        Toast.makeText(context, "EmailとPasswordを入力してください", Toast.LENGTH_LONG).show()
    }
    // エラー表示
    override fun showErrorToast() {
        Toast.makeText(context, "通信環境の良いところでお試しください", Toast.LENGTH_LONG).show()
    }
    // ログアウトトースト表示
    override fun signOut() {
        Toast.makeText(context, "ログアウト", Toast.LENGTH_LONG).show()
        setLoginView()
    }

    // activity終了
    private fun closeActivity() {
        val fade: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out_slide) as Animation
        val fadeOut: Animation = AnimationUtils.loadAnimation(context, R.anim.fade_out) as Animation

        tab.startAnimation(fade)
        tab.visibility = View.INVISIBLE
        mainFrame.startAnimation(fadeOut)
        mainFrame.visibility = View.INVISIBLE
        // val act = activity as MainActivity
        // main.backFragment()
    }

    // 成績表カード切り替え
    fun changeMode(mode: Int) {
        val fragment = when {
            mode == 1000 -> authFragment
            presenter.isSession() -> { when (mode) {
                0 -> GameRecordListFragment.newInstance("総合成績", 0)
                GameMode.NORMAL -> GameRecordListFragment.newInstance(resources.getString(R.string.syogi), GameMode.NORMAL)
                GameMode.BACKMOVE -> GameRecordListFragment.newInstance(resources.getString(R.string.annnan_syogi), GameMode.BACKMOVE)
                GameMode.CHAOS -> GameRecordListFragment.newInstance(resources.getString(R.string.chaos_syogi), GameMode.CHAOS)
                GameMode.TWOTIME -> GameRecordListFragment.newInstance(resources.getString(R.string.secound_syogi), GameMode.TWOTIME)
                GameMode.CHECKMATE -> GameRecordListFragment.newInstance(resources.getString(R.string.check_syogi), GameMode.CHECKMATE)
                GameMode.LIMIT -> GameRecordListFragment.newInstance(resources.getString(R.string.piece_syogi), GameMode.LIMIT)
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
        fun newInstance(): GameRecordRootFragment {
            return GameRecordRootFragment()
        }
    }
}
