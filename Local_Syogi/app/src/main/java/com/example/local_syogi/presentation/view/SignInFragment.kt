package com.example.local_syogi.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.SettingAccountContact

class SignInFragment : Fragment() {

   // private lateinit var button:Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        val mEmailField = view.findViewById(R.id.email_edit_text) as EditText
        val mPasswordField = view.findViewById(R.id.password_edit_text) as EditText


        //ログインボタン
        val button = view.findViewById(R.id.sign_in_button) as Button
        button.setOnClickListener {
            presenter.signIn("toshikihayashi4004@ezweb.ne.jp","884884")
        }
        return view
    }

    companion object {
        private lateinit var presenter: SettingAccountContact.Presenter

        @JvmStatic
        fun newInstance(mPresemter:SettingAccountContact.Presenter): SignInFragment {
            val fragment = SignInFragment()
            presenter = mPresemter
            return fragment
        }
    }


}
