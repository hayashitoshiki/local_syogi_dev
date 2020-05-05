package com.example.local_syogi.presentation.view.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.data.remote.AccountRepositoryImp
import com.example.local_syogi.domain.AccountUseCaseImp
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
    private var followList = listOf<FollowModel>()
    private var accountList = listOf<FollowModel>()
    private lateinit var arrayAdapter1:AccountCustomBaseAdapter
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
        followListView = view.findViewById(R.id.followList) as ListView
        searchListView = view.findViewById(R.id.searchList) as ListView
        searchEditText = view.findViewById(R.id.searchEditText) as EditText
        val pushButton = view.findViewById(R.id.pushButton) as Button

        presenter.getFollowList {
            followList = it
            arrayAdapter1 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, followList)
            followListView.adapter = arrayAdapter1
        }

//        followList = listOf(FollowModel("AAA", 1))
//        arrayAdapter1 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, followList)
//        followListView.adapter = arrayAdapter1

        pushButton.setOnClickListener{
            presenter.findAccount(searchEditText.text.toString()) {
                accountList = it
                arrayAdapter2 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, accountList)
                searchListView.adapter = arrayAdapter2
            }
        }

        // フォローリストの各ボタンタップ
        followListView.onItemClickListener = AdapterView.OnItemClickListener { _, parentView, position, _ ->
            if(parentView.id == parentView.deleteButton.id){
               when(parentView.deleteButton.text.toString()) {
                    "削除", "取消" -> {
                        presenter.deleteFollow(followList[position].userName)
                    }
                    //"承認" ->
                }
            }
        }

        // フォロー申請ボタンタップ
        searchListView.onItemClickListener = AdapterView.OnItemClickListener { _, parentView, position, _ ->
            parentView.deleteButton.setOnClickListener {
                when(parentView.deleteButton.text.toString()) {
                    "申請" -> presenter.addFollow(accountList[position].userName)
                    //"承認" ->
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
        accountList = followList
        arrayAdapter1 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, followList)
        followListView.adapter = arrayAdapter1
    }
}