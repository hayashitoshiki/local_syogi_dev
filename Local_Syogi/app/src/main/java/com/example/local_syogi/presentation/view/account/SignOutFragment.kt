package com.example.local_syogi.presentation.view.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
        val button = view.findViewById(R.id.logout) as Button
        val idTextView = view.findViewById(R.id.idTextView) as TextView
        val userNameTextView = view.findViewById(R.id.accountTextView) as TextView
        val emailTextView = view.findViewById(R.id.emailTextView) as TextView

        idTextView.text = presenter.getUid()
        userNameTextView.text = presenter.getUserName()
        emailTextView.text = presenter.getEmail()

        button.setOnClickListener {
            presenter.signOut()
        }

        return view
    }

    // エラー表示
    override fun showErrorToast() {
        Toast.makeText(context, "通信環境の良いところでお試しください", Toast.LENGTH_LONG).show()
    }
    // ログアウトトースト表示
    override fun signOut() {
        Toast.makeText(context, "ログアウト", Toast.LENGTH_LONG).show()
        val accountFragment: AuthenticationBaseFragment? = parentFragment as? AuthenticationBaseFragment

        when {
            accountFragment != null -> {
                accountFragment.setLoginView()
            }
            else -> {
                Toast.makeText(context, "親が一致しません" + accountFragment, Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(): SignOutFragment {
            return SignOutFragment()
        }
    }
}