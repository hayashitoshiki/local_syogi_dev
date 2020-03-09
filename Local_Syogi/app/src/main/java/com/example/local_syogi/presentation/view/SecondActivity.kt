package com.example.local_syogi.presentation.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.SettingAccountContact
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class SecondActivity : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.activity_sign_out, container, false)

        //ログインボタン
        val button = view.findViewById(R.id.logout) as Button
        button.setOnClickListener {
            presenter.signOut()
        }
        return view
    }

    companion object {
        private lateinit var presenter: SettingAccountContact.Presenter

        @JvmStatic
        fun newInstance(mPresemter: SettingAccountContact.Presenter): SignInFragment {
            val fragment = SignInFragment()
            presenter = mPresemter
            return fragment
        }
    }
}
