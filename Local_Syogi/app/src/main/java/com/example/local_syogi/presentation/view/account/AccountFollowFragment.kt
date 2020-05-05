package com.example.local_syogi.presentation.view.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.domain.AccountUseCaseImp
import com.example.local_syogi.domain.model.FollowModel
import com.example.local_syogi.presentation.contact.account.AccountFollowContact
import kotlinx.android.synthetic.main.list_item_follow.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class AccountFollowFragment : Fragment(), AccountFollowContact.View {

    private val presenter: AccountFollowContact.Presenter by inject { parametersOf(this) }

    private lateinit var searchEditText: EditText
    private lateinit var searchListView: ListView
    private var accountList = listOf<FollowModel>()
    private lateinit var arrayAdapter2:AccountCustomBaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account_follow, container, false)
        val followListView = view.findViewById(R.id.followList) as ListView
        searchListView = view.findViewById(R.id.searchList) as ListView
        searchEditText = view.findViewById(R.id.searchEditText) as EditText
        val pushButton = view.findViewById(R.id.pushButton) as Button

        val followList1: List<FollowModel> = presenter.getFollowList()
        val followList2: List<FollowModel> = presenter.getFollowRequestList()
        val followList3: List<FollowModel> = presenter.getFollowRequestMeList()
        val followListAll = followList1 + followList2 + followList3


        val arrayAdapter1 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, followListAll)
        arrayAdapter2 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, accountList)

        followListView.adapter = arrayAdapter1
        searchListView.adapter = arrayAdapter2
        pushButton.setOnClickListener{
            presenter.findAccount(searchEditText.text.toString()) {
                accountList = it
                arrayAdapter2 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, accountList)
                searchListView.adapter = arrayAdapter2
            }
        }

        // フォロー申請ボタンタップ
        searchListView.onItemClickListener = AdapterView.OnItemClickListener { _, parentView, position, _ ->
            parentView.deleteButton.setOnClickListener {
                presenter.addFollow(accountList[position].userName)
            }
        }

        return view
    }

    // フォローリストリセット
    override fun resetSearchList() {
        searchEditText.editableText.clear()
        accountList = listOf()
        arrayAdapter2 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, accountList)
        searchListView.adapter = arrayAdapter2
    }
}