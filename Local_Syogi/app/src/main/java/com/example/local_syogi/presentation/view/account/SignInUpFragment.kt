package com.example.local_syogi.presentation.view.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.SettingAccountContact

class SignInUpFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_sign_in_up, container, false)
        val mEmailField1 = view.findViewById(R.id.email_edit_text) as EditText
        val mEmailField2 = view.findViewById(R.id.email_edit_text2) as EditText
        val mPasswordField1 = view.findViewById(R.id.password_edit_text) as EditText
        val mPasswordField2 = view.findViewById(R.id.password_edit_text2) as EditText


        //ログインボタン
        val button = view.findViewById(R.id.sign_in_button) as Button
        button.setOnClickListener {
            presenter!!.signIn("toshikihayashi4004@ezweb.ne.jp","884884")
        }
        //新規作成ボタン
        val button2 = view.findViewById(R.id.sign_up_button) as Button
        button2.setOnClickListener {
            presenter!!.signUp(mEmailField2.text.toString(),mPasswordField2.text.toString())
        }
        return view
    }

    companion object {
        private var presenter: SettingAccountContact.Presenter? = null

        @JvmStatic
        fun newInstance(mPresemter:SettingAccountContact.Presenter): SignInUpFragment {
            val fragment =
                SignInUpFragment()
            presenter = mPresemter
            return fragment
        }
    }


}
