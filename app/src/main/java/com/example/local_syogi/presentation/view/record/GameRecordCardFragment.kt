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
import com.example.local_syogi.syogibase.data.entity.game.GameMode.BACKMOVE
import com.example.local_syogi.syogibase.data.entity.game.GameMode.CHAOS
import com.example.local_syogi.syogibase.data.entity.game.GameMode.CHECKMATE
import com.example.local_syogi.syogibase.data.entity.game.GameMode.LIMIT
import com.example.local_syogi.syogibase.data.entity.game.GameMode.NORMAL
import com.example.local_syogi.syogibase.data.entity.game.GameMode.TWOTIME
import kotlinx.android.synthetic.main.fragment_card_record_game.*

class GameRecordCardFragment : Fragment() {

    private val buttonList = arrayListOf<Button>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_card_record_game, container, false)

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

        // ボタン押下
        accountButton.setOnClickListener {
            changeMode(accountButton, 1000)
        }
        allButton.setOnClickListener {
            changeMode(it as Button, 0)
        }
        usualyButton.setOnClickListener {
            changeMode(it as Button, NORMAL)
        }
        annnanButton.setOnClickListener {
            changeMode(it as Button, BACKMOVE)
        }
        queenButton.setOnClickListener {
            changeMode(it as Button, CHAOS)
        }
        seccondButton.setOnClickListener {
            changeMode(it as Button, TWOTIME)
        }
        checkmateButton.setOnClickListener {
            changeMode(it as Button, CHECKMATE)
        }
        pieceLimitButton.setOnClickListener {
            changeMode(it as Button, LIMIT)
        }
        chaosButton.setOnClickListener {
            changeMode(it as Button, CHAOS)
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
        (parentFragment as? GameRecordRootFragment)?.changeMode(mode)
    }

    companion object {

        @JvmStatic
        fun newInstance(): GameRecordCardFragment {
            val fragment = GameRecordCardFragment()
            return fragment
        }
    }
}
