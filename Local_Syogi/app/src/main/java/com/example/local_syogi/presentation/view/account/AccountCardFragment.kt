package com.example.local_syogi.presentation.view.account

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.SettingAccountContact
import kotlinx.android.synthetic.main.fragment_card_account.*
import kotlinx.android.synthetic.main.fragment_usually_syogi.*

class AccountCardFragment: Fragment(){

    private val buttonList = arrayListOf<Button>()
    private lateinit var rootFragment:AccountRootFragment
    private var tab = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_card_account, container, false)
        val accountButton: Button = view.findViewById(R.id.accountButton)
        val allButton: Button = view.findViewById(R.id.allButton)
        val usuallyButton: Button = view.findViewById(R.id.usualyButton)
        val annanButton: Button = view.findViewById(R.id.annnanButton)
        val queenButton: Button = view.findViewById(R.id.queenButton)
        val secondButton: Button = view.findViewById(R.id.seccondButton)
        val checkmateButton: Button = view.findViewById(R.id.checkmateButton)
        val pieceLimitButton: Button = view.findViewById(R.id.pieceLimitButton)
        val chaosButton: Button = view.findViewById(R.id.chaosButton)
        rootFragment = parentFragment  as AccountRootFragment

        buttonList.add(allButton)
        buttonList.add(accountButton)
        buttonList.add(usuallyButton)
        buttonList.add(annanButton)
        buttonList.add(queenButton)
        buttonList.add(secondButton)
        buttonList.add(checkmateButton)
        buttonList.add(pieceLimitButton)
        buttonList.add(chaosButton)
        if(tab != -1){
            buttonList[tab].setTextColor(Color.parseColor("#795548"))
        }

        //ボタン押下
        accountButton.setOnClickListener{
            changeMode(accountButton, rootFragment.authFragment)
        }
        allButton.setOnClickListener{changeMode(allButton,
            ResultListFragment.newInstance("総合成績")
        ) }
        usuallyButton.setOnClickListener { changeMode(usuallyButton,
            ResultListFragment.newInstance(usuallyButton.text.toString())
        ) }
        annanButton.setOnClickListener { changeMode(annanButton,
            ResultListFragment.newInstance(annnanButton.text.toString())
        ) }
        queenButton.setOnClickListener { changeMode(queenButton,
            ResultListFragment.newInstance(queenButton.text.toString())
        ) }
        secondButton.setOnClickListener { changeMode(secondButton,
            ResultListFragment.newInstance(secondButton.text.toString())
        ) }
        checkmateButton.setOnClickListener { changeMode(checkmateButton,
            ResultListFragment.newInstance(checkmateButton.text.toString())
        ) }
        pieceLimitButton.setOnClickListener { changeMode(pieceLimitButton,
            ResultListFragment.newInstance(pieceLimitButton.text.toString())
        ) }
        chaosButton.setOnClickListener { changeMode(chaosButton,
            ResultListFragment.newInstance(chaosButton.text.toString())
        ) }
        return view
    }

    //タブ選択
    private fun changeMode(button:Button, fragment: Fragment) {
        for (btn in buttonList) {
            btn.setTextColor(Color.BLACK)
        }
        button.setTextColor(Color.parseColor("#795548"))
        val tab = buttonList.indexOf(button)
        if(button == accountButton || parentPresenter!!.isSession()){
            rootFragment.changeMode(fragment,tab)
        }else{
            rootFragment.changeMode(NotLoginFragment(),tab)
        }

    }

    companion object {
        private var parentPresenter: SettingAccountContact.Presenter? = null

        @JvmStatic
        fun newInstance(parentPresenter: SettingAccountContact.Presenter): AccountCardFragment {
            val fragment = AccountCardFragment()
            this.parentPresenter = parentPresenter
            return fragment
        }
    }
}