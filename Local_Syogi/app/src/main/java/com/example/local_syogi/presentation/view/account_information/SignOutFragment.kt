package com.example.local_syogi.presentation.view.account_information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.SettingAccountContact


class SignOutFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_sign_out, container, false)

        //ログインボタン
        val button = view.findViewById(R.id.logout) as Button
        button.setOnClickListener {
            presenter!!.signOut()
        }
        return view
    }

    companion object {
        private var presenter: SettingAccountContact.Presenter? = null

        @JvmStatic
        fun newInstance(mPresemter: SettingAccountContact.Presenter): SignOutFragment {
            val fragment =
                SignOutFragment()
            presenter = mPresemter
            return fragment
        }
    }
}
