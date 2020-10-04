package com.example.local_syogi.presentation.view.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.account.SignInUpContact
import kotlinx.android.synthetic.main.fragment_sign_in_up.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class SignInUpFragment : Fragment(), SignInUpContact.View {

    private val presenter: SignInUpContact.Presenter by inject { parametersOf(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in_up, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // ログインボタン
        sign_in_button.setOnClickListener {
            presenter.signIn(email_edit_text.text.toString(), password_edit_text.text.toString())
        }

        // 新規作成ボタン
        sign_up_button.setOnClickListener {
            presenter.signUp(
                userName_edit_text2.text.toString(),
                email_edit_text2.text.toString(),
                password_edit_text2.text.toString(),
                password_edit_text3.text.toString())
        }
    }

    // ログイン後(設定)画面を表示する
    override fun setInformationView() {
        val accountFragment: AuthenticationBaseFragment? = parentFragment as? AuthenticationBaseFragment

        when {
            accountFragment != null -> {
                accountFragment.setInformationView()
            }
            else -> {
                Toast.makeText(context, "親が一致しません" + accountFragment, Toast.LENGTH_LONG).show()
            }
        }
    }

    // エラー表示
    override fun showErrorEmailPassword() {
        Toast.makeText(context, "EmailとPasswordを入力してください", Toast.LENGTH_LONG).show()
    }
    // エラー表示
    override fun showErrorToast() {
        Toast.makeText(context, "通信環境の良いところでお試しください", Toast.LENGTH_LONG).show()
    }
    // ユーザー名不備
    override fun showErrorUserNameToast() {
        Toast.makeText(context, "ユーザー名を入力してください", Toast.LENGTH_LONG).show()
    }
    // Email不備
    override fun showErrorEmailToast() {
        Toast.makeText(context, "Emailを入力してください", Toast.LENGTH_LONG).show()
    }
    // パスワード不備
    override fun showErrorPasswordToast() {
        Toast.makeText(context, "パスワードを入力してください", Toast.LENGTH_LONG).show()
    }
    // パスワード不一致
    override fun showErrorPasswordEqualToast() {
        Toast.makeText(context, "パスワードが一致しません", Toast.LENGTH_LONG).show()
    }

    companion object {
        @JvmStatic
        fun newInstance(): SignInUpFragment {
            return SignInUpFragment()
        }
    }
}