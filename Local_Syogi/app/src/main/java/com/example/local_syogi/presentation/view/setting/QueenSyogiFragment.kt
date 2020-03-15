package com.example.local_syogi.presentation.view.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.local_syogi.presentation.contact.QueenSyogiContact

import com.example.local_syogi.R
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class QueenSyogiFragment : Fragment(), QueenSyogiContact.View {

    private val presenter: QueenSyogiContact.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_queen_syogi, container, false)
        val button = view.findViewById(R.id.startButton) as Button
        
        //UI処理
        button.setOnClickListener {
//            val intent = Intent(activity, GameActivity::class.java)
//            startActivity(intent)
            val mActivity: SettingRootFragment = parentFragment as SettingRootFragment
            mActivity.fadeOut()
        }
        return view
    }


}