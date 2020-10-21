package com.example.local_syogi.presentation.view.record

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.game.GamePlayBackContact
import com.example.local_syogi.presentation.view.game.GamePlayBackView
import com.example.local_syogi.syogibase.data.entity.game.GameLog
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import kotlinx.android.synthetic.main.fragment_game_play_back.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class GameRePlayFragment(private val log: MutableList<GameLog>, private val gameDetail: GameDetailSetitngModel) : Fragment(), GamePlayBackContact.View {

    private val presenter: GamePlayBackContact.Presenter by inject { parametersOf(this) }
    private lateinit var gameView: GamePlayBackView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_play_back, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameView = GamePlayBackView(context!!, game_container.width, game_container.height, log, gameDetail)
        game_container.addView(gameView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        backButton.setOnClickListener{
            gameView.backMove()
            gameView.invalidate()
        }
        goButton.setOnClickListener {
            gameView.goMove()
            gameView.invalidate()
        }
        backStartButton.setOnClickListener {
            gameView.backMoveFirst()
        }
        goEndButton.setOnClickListener {
            gameView.goMoveLast()
        }
        endButton.setOnClickListener {
            val customAlertView = layoutInflater.inflate(R.layout.dialog_custom, null)
            val builder = AlertDialog.Builder(context).setView(customAlertView).create()
            customAlertView.findViewById<TextView>(R.id.message)?.let{ it.text = "感想戦を終了しますか？"}
            customAlertView.findViewById<Button>(R.id.positive_button)?.let { it.text = "はい"
                it.setOnClickListener {
                    (parentFragment as GameRecordRootFragment).endRePlay()
                    builder.dismiss()
                }
            }
            customAlertView.findViewById<Button>(R.id.negative_button)?.let { it.text = "いいえ"
                it.setOnClickListener { builder.dismiss() }
            }
            builder.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            builder.show()
            builder.window.setLayout(750, 400)
        }
    }
}