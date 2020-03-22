package com.example.local_syogi.presentation.view.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.GameRecordRootContact
import com.example.local_syogi.presentation.contact.SettingAccountContact

class SignOutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_sign_out, container, false)

        Log.d("SignOut", "onCreateView")
        // ログインボタン
        val button = view.findViewById(R.id.logout) as Button
        button.setOnClickListener {
            if (presenter != null) {
                presenter!!.signOut()
            } else {
                presenter2!!.signOut()
            }
        }
        return view
    }

    companion object {
        private var presenter: SettingAccountContact.Presenter? = null
        private var presenter2: GameRecordRootContact.Presenter? = null

        @JvmStatic
        fun newInstance(mPresemter: SettingAccountContact.Presenter): SignOutFragment {
            val fragment =
                SignOutFragment()
            presenter = mPresemter
            return fragment
        }
        @JvmStatic
        fun newInstance2(mPresemter: GameRecordRootContact.Presenter): SignOutFragment {
            val fragment =
                SignOutFragment()
            presenter2 = mPresemter
            return fragment
        }
    }
}