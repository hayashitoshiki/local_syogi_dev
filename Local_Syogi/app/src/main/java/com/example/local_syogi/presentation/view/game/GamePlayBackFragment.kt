package com.example.local_syogi.presentation.view.game

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.GamePlayBackContact
import com.example.local_syogi.syogibase.data.local.GameLog
import com.example.local_syogi.syogibase.presentation.view.GameActivity

import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf



class GamePlayBackFragment(private val log:MutableList<GameLog>) : Fragment(), GamePlayBackContact.View {

    private val presenter: GamePlayBackContact.Presenter by inject { parametersOf(this) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_play_back, container, false)
        val frame = view.findViewById(R.id.frame) as FrameLayout
        val gameView = GameFreeView(context!!, frame.width,frame.height,log)
        val backButton = view.findViewById<Button>(R.id.backButton)
        val goButton = view.findViewById<Button>(R.id.goButton)
        val backStartButton = view.findViewById<Button>(R.id.backStartButton)
        val goEndButton = view.findViewById<Button>(R.id.goEndButton)
        val endButton = view.findViewById<Button>(R.id.endButton)

        frame.addView(gameView)
        backButton.setOnClickListener{
            gameView.backMove()
            gameView.invalidate()
        }
        goButton.setOnClickListener{
            gameView.goMove()
            gameView.invalidate()
        }
        backStartButton.setOnClickListener{
            gameView.backMoveFirst()
        }
        goEndButton.setOnClickListener{
            gameView.goMoveLast()
        }
        endButton.setOnClickListener{
            AlertDialog.Builder(context).setCancelable(false)
                .setMessage("感想戦を終了しますか？")
                .setPositiveButton("はい") { _, _ ->
                    val mActivity = activity as GameActivity
                    mActivity.end()
                }
                .setNegativeButton("いいえ",null)
                .create().show()

        }
        return view
    }
}