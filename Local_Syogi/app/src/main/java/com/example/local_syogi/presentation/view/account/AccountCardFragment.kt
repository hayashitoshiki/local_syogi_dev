package com.example.local_syogi.presentation.view.account

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.account.SettingAccountContact
import com.example.local_syogi.syogibase.data.entity.game.GameMode
import kotlinx.android.synthetic.main.fragment_card_account.*
import kotlinx.android.synthetic.main.fragment_card_account.allButton
import kotlinx.android.synthetic.main.fragment_card_account.annnanButton
import kotlinx.android.synthetic.main.fragment_card_account.chaosButton
import kotlinx.android.synthetic.main.fragment_card_account.checkmateButton
import kotlinx.android.synthetic.main.fragment_card_account.pieceLimitButton
import kotlinx.android.synthetic.main.fragment_card_account.queenButton
import kotlinx.android.synthetic.main.fragment_card_account.seccondButton
import kotlinx.android.synthetic.main.fragment_card_account.usualyButton

class AccountCardFragment : Fragment() {

    private val buttonList = arrayListOf<Button>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_card_account, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        buttonList.add(allButton)
        buttonList.add(accountButton)
        buttonList.add(usualyButton)
        buttonList.add(annnanButton)
        buttonList.add(queenButton)
        buttonList.add(seccondButton)
        buttonList.add(checkmateButton)
        buttonList.add(pieceLimitButton)
        buttonList.add(chaosButton)
        buttonList.add(accountFollowButton)

        // ボタン押下
        accountButton.setOnClickListener {
            changeMode(accountButton, 1000)
        }
        accountFollowButton.setOnClickListener {
            changeMode(it as Button, 2000)
        }
        allButton.setOnClickListener {
            changeMode(it as Button, 0)
        }
        usualyButton.setOnClickListener {
            changeMode(it as Button, GameMode.NORMAL)
        }
        annnanButton.setOnClickListener {
            changeMode(it as Button, GameMode.BACKMOVE)
        }
        queenButton.setOnClickListener {
            changeMode(it as Button, GameMode.CHAOS)
        }
        seccondButton.setOnClickListener {
            changeMode(it as Button, GameMode.TWOTIME)
        }
        checkmateButton.setOnClickListener {
            changeMode(it as Button, GameMode.CHECKMATE)
        }
        pieceLimitButton.setOnClickListener {
            changeMode(it as Button, GameMode.LIMIT)
        }
        chaosButton.setOnClickListener {
            changeMode(it as Button, GameMode.CHAOS)
        }
    }

    // タブ選択
    private fun changeMode(button: Button, mode: Int) {

        buttonList.forEach { btn -> when (btn) {
            button -> {
                btn.setTextColor(Color.parseColor("#795548"))
                btn.typeface = Typeface.DEFAULT_BOLD
            }
            else -> {
                btn.setTextColor(Color.BLACK)
                btn.setTypeface(null, Typeface.ITALIC)
            }
        } }
        (parentFragment as? AccountRootFragment)?.changeMode(mode)

    }

    companion object {

        @JvmStatic
        fun newInstance(parentPresenter: SettingAccountContact.Presenter): AccountCardFragment {
            val fragment = AccountCardFragment()
            return fragment
        }
    }
}