package com.example.local_syogi.presentation.view.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.domain.model.FollowModel
import com.example.local_syogi.presentation.contact.account.AccountFollowContact
import kotlinx.android.synthetic.main.activity_game_setting.view.*
import kotlinx.android.synthetic.main.list_item_follow.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AccountFollowFragment : Fragment(), AccountFollowContact.View {

    private val presenter: AccountFollowContact.Presenter by inject { parametersOf(this) }

    private lateinit var searchEditText: EditText
    private lateinit var searchListView: ListView
    private lateinit var followListView: ListView
    private lateinit var progressLayout: ConstraintLayout
    private var followList = listOf<FollowModel>()
    private var accountList = listOf<FollowModel>()
    private lateinit var arrayAdapter1: AccountCustomBaseAdapter
    private lateinit var arrayAdapter2: AccountCustomBaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account_follow, container, false)
        followListView = view.findViewById(R.id.followList)
        searchListView = view.findViewById(R.id.searchList)
        searchEditText = view.findViewById(R.id.searchEditText)
        progressLayout = view.findViewById(R.id.progressLayout)
        val pushButton = view.findViewById(R.id.pushButton) as Button

        presenter.getFollowList {
            followList = it
            arrayAdapter1 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, followList)
            followListView.adapter = arrayAdapter1
            progressLayout.visibility = View.GONE
        }

        pushButton.setOnClickListener {
            presenter.findAccount(searchEditText.text.toString()) {
                accountList = it
                arrayAdapter2 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, accountList)
                searchListView.adapter = arrayAdapter2
            }
        }

        // フォローリストの各ボタンタップ
        followListView.onItemClickListener = AdapterView.OnItemClickListener { _, parentView, position, _ ->
            if (parentView.id == parentView.deleteButton.id) {
               when (parentView.deleteButton.text.toString()) {
                    "削除", "取消" -> {
                        if(progressLayout.visibility != View.VISIBLE) {
                            progressLayout.visibility = View.VISIBLE
                            presenter.deleteFollow(followList[position].userId)
                        }
                    }
                    "承認" -> {
                        if(progressLayout.visibility != View.VISIBLE) {
                            progressLayout.visibility = View.VISIBLE
                            presenter.updateFollow(followList[position].userId)
                        }
                    }
                }
            }
        }

        // フォロー申請ボタンタップ
        searchListView.onItemClickListener = AdapterView.OnItemClickListener { _, parentView, position, _ ->
            parentView.deleteButton.setOnClickListener {
                when (parentView.deleteButton.text.toString()) {
                    "申請" -> {
                        if(progressLayout.visibility != View.VISIBLE) {
                            progressLayout.visibility = View.VISIBLE
                            presenter.addFollow(accountList[position].userId)
                        }
                    }
                }
            }
        }

        return view
    }

    // 検索結果リストリセット
    override fun resetSearchList() {
        searchEditText.editableText.clear()
        accountList = listOf()
        arrayAdapter2 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, accountList)
        searchListView.adapter = arrayAdapter2
    }

    // フォローリスト更新
    override fun updateFollowList(followList: List<FollowModel>) {
        progressLayout.visibility = View.GONE
        accountList = followList
        arrayAdapter1 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, followList)
        followListView.adapter = arrayAdapter1
    }
}