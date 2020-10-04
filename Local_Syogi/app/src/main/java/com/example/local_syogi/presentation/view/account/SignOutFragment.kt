package com.example.local_syogi.presentation.view.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.account.SignOutContact
import kotlinx.android.synthetic.main.fragment_sign_out.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SignOutFragment : Fragment(), SignOutContact.View {

    private val presenter: SignOutContact.Presenter by inject { parametersOf(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_out, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        idTextView.text = presenter.getUid()
        accountTextView.text = presenter.getUserName()
        emailTextView.text = presenter.getEmail()

        logout.setOnClickListener {
            presenter.signOut()
        }
    }

    // エラー表示
    override fun showErrorToast() {
        Toast.makeText(context, "通信環境の良いところでお試しください", Toast.LENGTH_LONG).show()
    }

    // ログアウトトースト表示
    override fun signOut() {
        Toast.makeText(context, "ログアウト", Toast.LENGTH_LONG).show()

        (parentFragment as? AuthenticationBaseFragment)?.let {
            it.setLoginView()
        } ?:run {
            Log.d("TAG","親フラグメントが一致しません")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(): SignOutFragment {
            return SignOutFragment()
        }
    }
}