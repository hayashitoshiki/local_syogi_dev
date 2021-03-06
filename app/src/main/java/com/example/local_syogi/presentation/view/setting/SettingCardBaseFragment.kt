package com.example.local_syogi.presentation.view.setting

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.syogibase.data.entity.game.GameMode

class SettingCardBaseFragment : Fragment() {

    private val buttonList = arrayListOf<Button>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = if (mode == 1) {
            GameMode.rate = false
            inflater.inflate(R.layout.fragment_select_normal, container, false)
        } else {
            GameMode.rate = true
            inflater.inflate(R.layout.fragment_select_rate, container, false)
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val usuallyButton: Button = view!!.findViewById(R.id.usualyButton)
        val annanButton: Button = view!!.findViewById(R.id.annnanButton)
        val queenButton: Button = view!!.findViewById(R.id.queenButton)
        val secondButton: Button = view!!.findViewById(R.id.seccondButton)
        val checkmateButton: Button = view!!.findViewById(R.id.checkmateButton)
        val pieceLimitButton: Button = view!!.findViewById(R.id.pieceLimitButton)
        val chaosButton: Button = view!!.findViewById(R.id.chaosButton)

        buttonList.add(usuallyButton)
        buttonList.add(annanButton)
        buttonList.add(queenButton)
        buttonList.add(secondButton)
        buttonList.add(checkmateButton)
        buttonList.add(pieceLimitButton)
        buttonList.add(chaosButton)

        // ボタン押下
        usuallyButton.setOnClickListener { changeMode(usuallyButton,
            SettingSyogiBaseFragment.newInstance(1, mode)
        ) }
        annanButton.setOnClickListener { changeMode(annanButton,
            SettingSyogiBaseFragment.newInstance(3, mode)
        ) }
        queenButton.setOnClickListener { changeMode(queenButton,
            SettingSyogiBaseFragment.newInstance(6, mode)
        ) }
        secondButton.setOnClickListener { changeMode(secondButton,
            SettingSyogiBaseFragment.newInstance(4, mode)
        ) }
        checkmateButton.setOnClickListener { changeMode(checkmateButton,
            SettingSyogiBaseFragment.newInstance(2, mode)
        ) }
        pieceLimitButton.setOnClickListener { changeMode(pieceLimitButton,
            SettingSyogiBaseFragment.newInstance(5, mode)
        ) }
        chaosButton.setOnClickListener { changeMode(chaosButton,
            SettingSyogiBaseFragment.newInstance(7, mode)
        ) }
    }

    // タブ選択
    private fun changeMode(button: Button, fragment: Fragment) {
        for (btn in buttonList) {
            btn.setTextColor(Color.WHITE)
        }
        button.setTextColor(Color.parseColor("#795548"))
        val tab = buttonList.indexOf(button)
        (parentFragment as SettingRootFragment).changeMode(fragment, tab)
    }

    companion object {

        private var mode = 0
        private var tab = -1

        @JvmStatic
        fun newInstance(mode: Int, tab: Int): SettingCardBaseFragment {
            val fragment =
                SettingCardBaseFragment()
            Companion.mode = mode
            Companion.tab = tab
            return fragment
        }
    }
}
