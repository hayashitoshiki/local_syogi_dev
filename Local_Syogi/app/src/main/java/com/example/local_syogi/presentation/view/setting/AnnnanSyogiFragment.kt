package com.example.local_syogi.presentation.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.setting.AnnnanSyogiContact
import com.example.local_syogi.syogibase.data.game.GameMode
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AnnnanSyogiFragment : Fragment(), AnnnanSyogiContact.View {

    private val presenter: AnnnanSyogiContact.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_annnan_syogi, container, false)
        val button = view.findViewById(R.id.startButton) as Button

        // UI処理
        button.setOnClickListener {
            GameMode.annan = true
//            val intent = Intent(activity, GameActivity::class.java)
//            startActivity(intent)
            val mActivity: SettingRootFragment = parentFragment as SettingRootFragment
            mActivity.fadeOut()
        }
        return view
    }
}