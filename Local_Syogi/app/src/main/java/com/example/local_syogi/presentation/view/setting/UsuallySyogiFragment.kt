package com.example.local_syogi.presentation.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.UsuallySyogiContact
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class UsuallySyogiFragment : Fragment(), UsuallySyogiContact.View {

    private val presenter: UsuallySyogiContact.Presenter by inject { parametersOf(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_usually_syogi, container, false)
        val button = view.findViewById(R.id.startButton) as Button

        // UI処理
        button.setOnClickListener {
//            val intent = Intent(activity, GameActivity::class.java)
//            startActivity(intent)
            val mActivity: SettingRootFragment = parentFragment as SettingRootFragment
            mActivity.fadeOut()
        }
        return view
    }
}