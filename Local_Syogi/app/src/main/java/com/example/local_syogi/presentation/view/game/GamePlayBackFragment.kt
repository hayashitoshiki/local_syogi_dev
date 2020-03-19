package com.example.local_syogi.syogibase.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.local_syogi.syogibase.presentation.contact.GamePlayBackContact

import com.example.local_syogi.syogibase.presentation.R
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf



class GamePlayBackFragment : Fragment(), GamePlayBackContact.View {

    private val presenter: GamePlayBackContact.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_game_play_back, container, false)

        return view
    }


}