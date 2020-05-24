package com.example.local_syogi.presentation.view.record

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.game.GamePlayBackContact
import com.example.local_syogi.presentation.view.game.GameFreeView
import com.example.local_syogi.syogibase.data.entity.game.GameLog
import com.example.local_syogi.syogibase.domain.model.GameDetailSetitngModel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GameRePlayFragment(private val log: MutableList<GameLog>, private val gameDetail: GameDetailSetitngModel) : Fragment(), GamePlayBackContact.View {

    private val presenter: GamePlayBackContact.Presenter by inject { parametersOf(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_play_back, container, false)
        val frame = view.findViewById(R.id.frame) as FrameLayout
        val gameView = GameFreeView(context!!, frame.width, frame.height, log, gameDetail)
        val backButton = view.findViewById<Button>(R.id.backButton)
        val goButton = view.findViewById<Button>(R.id.goButton)
        val backStartButton = view.findViewById<Button>(R.id.backStartButton)
        val goEndButton = view.findViewById<Button>(R.id.goEndButton)
        val endButton = view.findViewById<Button>(R.id.endButton)

        frame.addView(gameView)
        backButton.setOnClickListener {
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
        return view
    }
}