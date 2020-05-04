package com.example.local_syogi.presentation.view.account

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.data.entity.AccountEntity
import com.example.local_syogi.domain.AccountUseCaseImp
import com.example.local_syogi.domain.model.FollowModel
import com.example.local_syogi.presentation.contact.account.AccountFollowContact
import kotlinx.android.synthetic.main.fragment_account_follow.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class AccountFollowFragment : Fragment(), AccountFollowContact.View {

    private val presenter: AccountFollowContact.Presenter by inject { parametersOf(this) }

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
        val searchListView = view.findViewById(R.id.searchList) as ListView
        val searchEditText = view.findViewById(R.id.searchEditText) as EditText
        val pushButton = view.findViewById(R.id.pushButton) as Button

        val followList1: List<FollowModel> = presenter.getFollowList()
        val followList2: List<FollowModel> = presenter.getFollowRequestList()
        val followList3: List<FollowModel> = presenter.getFollowRequestMeList()
        val followListAll = followList1 + followList2 + followList3
        var accontList = listOf<FollowModel>()

        val arrayAdapter1 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, followListAll)
        var arrayAdapter2 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, accontList)

        followListView.adapter = arrayAdapter1
        searchListView.adapter = arrayAdapter2
        pushButton.setOnClickListener{
            presenter.findAccount(searchEditText.text.toString()) {
                accontList = it
                arrayAdapter2 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, accontList)
                searchListView.adapter = arrayAdapter2
            }
        }

        return view
    }
}