package com.example.local_syogi.presentation.view.record

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.record.GameRecordRootContact
import com.example.local_syogi.presentation.view.account.NotLoginFragment
import com.example.local_syogi.syogibase.data.entity.game.GameMode.BACKMOVE
import com.example.local_syogi.syogibase.data.entity.game.GameMode.CHAOS
import com.example.local_syogi.syogibase.data.entity.game.GameMode.CHECKMATE
import com.example.local_syogi.syogibase.data.entity.game.GameMode.LIMIT
import com.example.local_syogi.syogibase.data.entity.game.GameMode.NORMAL
import com.example.local_syogi.syogibase.data.entity.game.GameMode.TWOTIME

class GameRecordCardFragment : Fragment() {

    private val buttonList = arrayListOf<Button>()
    private lateinit var rootFragment: GameRecordRootFragment
    private lateinit var accountButton: Button
    private var tab = -1
    private var colorPrevious: Int = Color.parseColor("#795548")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_card_record_game, container, false)
        accountButton = view.findViewById(R.id.accountButton)
        val allButton: Button = view.findViewById(R.id.allButton)
        val usuallyButton: Button = view.findViewById(R.id.usualyButton)
        val annanButton: Button = view.findViewById(R.id.annnanButton)
        val queenButton: Button = view.findViewById(R.id.queenButton)
        val secondButton: Button = view.findViewById(R.id.seccondButton)
        val checkmateButton: Button = view.findViewById(R.id.checkmateButton)
        val pieceLimitButton: Button = view.findViewById(R.id.pieceLimitButton)
        val chaosButton: Button = view.findViewById(R.id.chaosButton)
        rootFragment = parentFragment as GameRecordRootFragment

        buttonList.add(allButton)
        buttonList.add(accountButton)
        buttonList.add(usuallyButton)
        buttonList.add(annanButton)
        buttonList.add(queenButton)
        buttonList.add(secondButton)
        buttonList.add(checkmateButton)
        buttonList.add(pieceLimitButton)
        buttonList.add(chaosButton)
        if (tab != -1) {
            buttonList[tab].setTextColor(Color.parseColor("#795548"))
        }

        // ボタン押下
        accountButton.setOnClickListener {
            changeMode(accountButton, rootFragment.authFragment)
        }
        allButton.setOnClickListener { changeMode(allButton,
            GameRecordListFragment.newInstance("総合成績", 0)
        ) }
        usuallyButton.setOnClickListener { changeMode(usuallyButton,
            GameRecordListFragment.newInstance(usuallyButton.text.toString(), NORMAL)
        ) }
        annanButton.setOnClickListener { changeMode(annanButton,
            GameRecordListFragment.newInstance(annanButton.text.toString(), BACKMOVE)
        ) }
        queenButton.setOnClickListener { changeMode(queenButton,
            GameRecordListFragment.newInstance(queenButton.text.toString(), CHAOS)
        ) }
        secondButton.setOnClickListener { changeMode(secondButton,
            GameRecordListFragment.newInstance(secondButton.text.toString(), TWOTIME)
        ) }
        checkmateButton.setOnClickListener { changeMode(checkmateButton,
            GameRecordListFragment.newInstance(checkmateButton.text.toString(), CHECKMATE)
        ) }
        pieceLimitButton.setOnClickListener { changeMode(pieceLimitButton,
            GameRecordListFragment.newInstance(pieceLimitButton.text.toString(), LIMIT)
        ) }
        chaosButton.setOnClickListener { changeMode(chaosButton,
            GameRecordListFragment.newInstance(chaosButton.text.toString(), CHAOS)
        ) }
        return view
    }

    // タブ選択
    private fun changeMode(button: Button, fragment: Fragment) {
//        for (btn in buttonList) {
//           // btn.setTextColor(Color.BLACK)
//        }
        if (tab != -1) {
            buttonList[tab].setTextColor(colorPrevious)
            buttonList[tab].setTypeface(null, Typeface.ITALIC)
        }
        tab = buttonList.indexOf(button)
        colorPrevious = buttonList[tab].currentTextColor
        button.setTextColor(Color.parseColor("#795548"))
        button.typeface = Typeface.DEFAULT_BOLD

        if (button == accountButton || parentPresenter!!.isSession()) {
            rootFragment.changeMode(fragment, tab)
        } else {
            rootFragment.changeMode(NotLoginFragment(), tab)
        }
    }

    companion object {
        private var parentPresenter: GameRecordRootContact.Presenter? = null

        @JvmStatic
        fun newInstance(parentPresenter: GameRecordRootContact.Presenter): GameRecordCardFragment {
            val fragment = GameRecordCardFragment()
            this.parentPresenter = parentPresenter
            return fragment
        }
    }
}