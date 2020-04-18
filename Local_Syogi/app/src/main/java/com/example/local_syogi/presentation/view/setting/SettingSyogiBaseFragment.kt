package com.example.local_syogi.presentation.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.setting.SettingSyogiBaseContact
import com.example.local_syogi.syogibase.data.game.GameMode
import com.example.local_syogi.syogibase.presentation.view.GameSettingSharedPreferences
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SettingSyogiBaseFragment : Fragment(), SettingSyogiBaseContact.View {

    private val presenter: SettingSyogiBaseContact.Presenter by inject { parametersOf(this) }

    private lateinit var radioButton3: RadioButton
    private lateinit var radioButton5: RadioButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = when (mode) {
            1 -> inflater.inflate(R.layout.fragment_usually_syogi, container, false)
            2 -> inflater.inflate(R.layout.fragment_checkmate_syogi, container, false)
            3 -> inflater.inflate(R.layout.fragment_annnan_syogi, container, false)
            4 -> inflater.inflate(R.layout.fragment_seccond_syogi, container, false)
            5 -> inflater.inflate(R.layout.fragment_piece_limit_syogi, container, false)
            6 -> inflater.inflate(R.layout.fragment_queen_syogi, container, false)
            7 -> inflater.inflate(R.layout.fragment_chaos_syogi, container, false)
            else -> inflater.inflate(R.layout.fragment_usually_syogi, container, false)
        }
        val button = view.findViewById(R.id.startButton) as Button
        val sharedPreferences = GameSettingSharedPreferences(context!!)
        radioButton3 = view.findViewById(R.id.radioButton3)
        radioButton5 = view.findViewById(R.id.radioButton5)
        when (sharedPreferences.getRateTimeLimit()) {
            3 * 60 * 1000 -> radioButton3.isChecked = true
            5 * 60 * 1000 -> radioButton5.isChecked = true
        }
        if (card == 1) {
            setOfflineMode()
        }

        // UI処理
        button.setOnClickListener {
            when (mode) {
                2 -> GameMode.checkmate = true
                3 -> GameMode.annan = true
                4 -> GameMode.twoTimes = true
                5 -> GameMode.pieceLimit = true
            }
            val mActivity: SettingRootFragment = parentFragment as SettingRootFragment
            mActivity.fadeOut()
        }
        radioButton3.setOnCheckedChangeListener { _, _ ->
            if (radioButton3.isChecked) {
                radioButton5.isChecked = false
                sharedPreferences.setRateTimeLimit(3 * 60 * 1000)
            }
        }
        radioButton5.setOnCheckedChangeListener { _, _ ->
            if (radioButton5.isChecked) {
                radioButton3.isChecked = false
                sharedPreferences.setRateTimeLimit(5 * 60 * 1000)
            }
        }
            return view
    }

    fun setOnlineMode() {
        radioButton3.visibility = View.VISIBLE
        radioButton5.visibility = View.VISIBLE
    }

    fun setOfflineMode() {
        radioButton3.visibility = View.INVISIBLE
        radioButton5.visibility = View.INVISIBLE
    }

    companion object {
        var mode = 1
        /**
         * modeの引数
         * １：通常将棋
         * ２：王手将棋
         * ３：安南将棋
         * ４：2手差し将棋
         * ５：持ち駒制限将棋
         * ６：クイーン将棋
         */
        var card = 1

        /**
         * cardの引数
         * １：オフライン対戦
         * ２：オンライン対戦
         */

        @JvmStatic
        fun newInstance(mode: Int, card: Int): SettingSyogiBaseFragment {
            val fragment = SettingSyogiBaseFragment()
            this.mode = mode
            this.card = card
            return fragment
        }
    }
}