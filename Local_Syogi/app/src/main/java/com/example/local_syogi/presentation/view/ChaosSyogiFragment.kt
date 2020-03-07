package com.example.local_syogi.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.local_syogi.presentation.contact.ChaosSyogiContact

import com.example.local_syogi.R
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/*
 *  DI用クラスに以下の１行を追加してください
 */


class ChaosSyogiFragment : Fragment(), ChaosSyogiContact.View {

    private val presenter: ChaosSyogiContact.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_chaos_syogi, container, false)

        return view
    }


}