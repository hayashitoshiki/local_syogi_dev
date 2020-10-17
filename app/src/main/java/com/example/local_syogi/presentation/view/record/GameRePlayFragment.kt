package com.example.local_syogi.presentation.view.record

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.game.GamePlayBackContact
import com.example.local_syogi.presentation.view.game.GameFreeView
import com.example.local_syogi.syogibase.data.entity.game.GameLog
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import kotlinx.android.synthetic.main.fragment_game_play_back.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GameRePlayFragment(private val log: MutableList<GameLog>, private val gameDetail: GameDetailSetitngModel) : Fragment(), GamePlayBackContact.View {

    private val presenter: GamePlayBackContact.Presenter by inject { parametersOf(this) }
    private lateinit var gameView: GameFreeView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_play_back, container, false)
        gameView = GameFreeView(context!!, game_container.width, game_container.height, log, gameDetail)
        game_container.addView(gameView)

        return view
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
            AlertDialog.Builder(context).setCancelable(false)
                .setMessage("感想戦を終了しますか？")
                .setPositiveButton("はい") { _, _ ->
                    (parentFragment as GameRecordRootFragment).endRePlay()
                }
                .setNegativeButton("いいえ", null)
                .create().show()
        }
    }
}