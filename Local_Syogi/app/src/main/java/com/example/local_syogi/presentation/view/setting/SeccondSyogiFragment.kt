package com.example.local_syogi.presentation.view.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.local_syogi.presentation.contact.SeccondSyogiContact

import com.example.local_syogi.R
import com.example.local_syogi.syogibase.data.local.GameMode
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/*
 *  DI用クラスに以下の１行を追加してください
 */


class SeccondSyogiFragment : Fragment(), SeccondSyogiContact.View {

    private val presenter: SeccondSyogiContact.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seccond_syogi, container, false)
        val button = view.findViewById(R.id.startButton) as Button

        //UI処理
        button.setOnClickListener {
            GameMode.twoTimes = true
//            val intent = Intent(activity, GameActivity::class.java)
//            startActivity(intent)
            val mActivity: SettingActivity = parentFragment as SettingActivity
            mActivity.fadeOut()
        }
        return view
    }


}