package com.example.local_syogi.presentation.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.SettingAccountContact

import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/*
 *  DI用クラスに以下の１行を追加してください
 */


class SettingAccountFragment : AppCompatActivity(), SettingAccountContact.View {

    private val presenter: SettingAccountContact.Presenter by inject { parametersOf(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_setting_account)
        presenter.onStart()
    }

    //ログイン画面を表示する
    override fun setLoginView() {
        Log.d("Main","ログイン")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, SignInFragment.newInstance(presenter))
            .commit()
    }

    //ログイン後(設定)画面を表示する
    override fun setInformationView() {
        Log.d("Main","設定画面")
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment, SecondActivity.newInstance(presenter))
            .commit()
    }

    //エラー表示
    override fun showErrorEmailPassword(){
        Toast.makeText(applicationContext, "EmailとPasswordを入力してください", Toast.LENGTH_LONG).show()
    }
    //エラー表示
    override fun showErrorToast() {
        Toast.makeText(applicationContext, "通信環境の良いところでお試しください", Toast.LENGTH_LONG).show()
    }
    //ログアウトトースト表示
    override fun signOut(){
        Toast.makeText(applicationContext, "ログアウト", Toast.LENGTH_LONG).show()
        setLoginView()
    }



}