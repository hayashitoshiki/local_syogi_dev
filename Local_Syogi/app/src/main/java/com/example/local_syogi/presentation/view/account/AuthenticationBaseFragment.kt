package com.example.local_syogi.presentation.view.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.account.AuthenticationBaseContact
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AuthenticationBaseFragment : Fragment(), AuthenticationBaseContact.View {

    private val presenter: AuthenticationBaseContact.Presenter by inject { parametersOf(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sign_base, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (presenter.isSession()) {
            setInformationView()
        } else {
            setLoginView()
        }
    }

    // ログインViewを表示する
    fun setLoginView() {
        Log.d("Main", "ログイン")
        if (isAdded) {
            childFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out
                )
                .replace(R.id.fragment, SignInUpFragment.newInstance())
                .commit()
        }
    }

    // ログイン後(設定)画面を表示する
    fun setInformationView() {
        Log.d("Main", "設定画面")

        if (isAdded) {
            childFragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.fade_in,
                    R.anim.fade_out
                )
                .replace(R.id.fragment, SignOutFragment.newInstance())
                .commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): AuthenticationBaseFragment {
            return AuthenticationBaseFragment()
        }
    }
}