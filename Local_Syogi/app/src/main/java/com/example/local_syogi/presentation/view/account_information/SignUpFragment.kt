package com.example.local_syogi.presentation.view.account_information

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.SettingAccountContact


class SignUpFragment : Fragment() {

//    private lateinit var mEmailField: EditText
//    private lateinit var mPasswordField: EditText
    private var button: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        val mEmailField = view.findViewById(R.id.email_edit_text) as EditText
        val mPasswordField = view.findViewById(R.id.password_edit_text) as EditText

        //新規作成ボタン
        button = view.findViewById(R.id.sign_up_button) as Button
        button!!.setOnClickListener {
            presenter.signUp(mEmailField.text.toString(),mPasswordField.text.toString())
        }
        return view
    }


    companion object {
        private lateinit var presenter: SettingAccountContact.Presenter

        @JvmStatic
        fun newInstance(mPresemter: SettingAccountContact.Presenter): SignUpFragment {
            val fragment =
                SignUpFragment()
            presenter = mPresemter
            return fragment
        }
    }

}