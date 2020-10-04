package com.example.local_syogi.presentation.view.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.domain.model.FollowModel
import com.example.local_syogi.presentation.contact.account.AccountFollowContact
import kotlinx.android.synthetic.main.fragment_account_follow.*
import kotlinx.android.synthetic.main.list_item_follow.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AccountFollowFragment : Fragment(), AccountFollowContact.View {

    private val presenter: AccountFollowContact.Presenter by inject { parametersOf(this) }

    private var followsList = listOf<FollowModel>()
    private var accountsList = listOf<FollowModel>()
    private lateinit var arrayAdapter1: AccountCustomBaseAdapter
    private lateinit var arrayAdapter2: AccountCustomBaseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account_follow, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter.getFollowList {
            followsList = it
            arrayAdapter1 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, followsList)
            followList.adapter = arrayAdapter1
            progressLayout.visibility = View.GONE
        }

        pushButton.setOnClickListener {
            presenter.findAccount(searchEditText.text.toString()) {
                accountsList = it
                arrayAdapter2 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, accountsList)
                searchList.adapter = arrayAdapter2
            }
        }

        // フォローリストの各ボタンタップ
        followList.onItemClickListener = AdapterView.OnItemClickListener { _, parentView, position, _ ->
            if (parentView.id == parentView.deleteButton.id) {
                when (parentView.deleteButton.text.toString()) {
                    "削除", "取消" -> {
                        if(progressLayout.visibility != View.VISIBLE) {
                            progressLayout.visibility = View.VISIBLE
                            presenter.deleteFollow(followsList[position].userId)
                        }
                    }
                    "承認" -> {
                        if(progressLayout.visibility != View.VISIBLE) {
                            progressLayout.visibility = View.VISIBLE
                            presenter.updateFollow(followsList[position].userId)
                        }
                    }
                }
            }
        }

        // フォロー申請ボタンタップ
        searchList.onItemClickListener = AdapterView.OnItemClickListener { _, parentView, position, _ ->
            parentView.deleteButton.setOnClickListener {
                when (parentView.deleteButton.text.toString()) {
                    "申請" -> {
                        if(progressLayout.visibility != View.VISIBLE) {
                            progressLayout.visibility = View.VISIBLE
                            presenter.addFollow(accountsList[position].userId)
                        }
                    }
                }
            }
        }
    }

    // 検索結果リストリセット
    override fun resetSearchList() {
        searchEditText.editableText.clear()
        accountsList = listOf()
        arrayAdapter2 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, accountsList)
        searchList.adapter = arrayAdapter2
    }

    // フォローリスト更新
    override fun updateFollowList(followsList: List<FollowModel>) {
        progressLayout.visibility = View.GONE
        accountsList = followsList
        arrayAdapter1 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, followsList)
        followList.adapter = arrayAdapter1
    }
}