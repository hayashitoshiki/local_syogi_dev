package com.example.local_syogi.presentation.view.account

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.RateCardContact
import com.example.local_syogi.presentation.contact.SettingAccountContact


import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/*
 *  DI用クラスに以下の１行を追加してください
 */



class AuthenticationBaseFragment : Fragment(), RateCardContact.View {

    private val presenter: RateCardContact.Presenter by inject { parametersOf(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_base, container, false)

        parentPresenter!!.onStart()
        return view
    }

    //ログインViewを表示する
    fun setLoginView() {
        Log.d("Main","ログイン")
        val signIn = SignInUpFragment.newInstance(parentPresenter!!)
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment, signIn)
            .commit()
    }

    //ログイン後(設定)画面を表示する
    fun setInformationView() {
        Log.d("Main","設定画面")
        val signIn =SignOutFragment.newInstance(parentPresenter!!)
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment, signIn)
            .commit()
    }


    companion object {
        private var parentPresenter: SettingAccountContact.Presenter? = null

        @JvmStatic
        fun newInstance(parentPresenter: SettingAccountContact.Presenter): AuthenticationBaseFragment {
            val fragment = AuthenticationBaseFragment()
            this.parentPresenter = parentPresenter
            return fragment
        }
    }

}