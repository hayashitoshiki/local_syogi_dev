package com.example.local_syogi.presentation.view.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.domain.model.FollowModel
import com.example.local_syogi.presentation.contact.account.AccountFollowContact
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
        val followListView1: ListView = view.findViewById(R.id.followList)
        // val followListView2: ListView = view.findViewById(R.id.follow2List)
        // val followListView3: ListView = view.findViewById(R.id.follow3List)

        val followList1: List<FollowModel> = presenter.getFollowList()
        val followList2: List<FollowModel> = presenter.getFollowRequestList()
        val followList3: List<FollowModel> = presenter.getFollowRequestMeList()
        val followListAll = followList1 + followList2 + followList3

        val arrayAdapter1 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, followListAll)
        // val arrayAdapter2 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, followList2)
        // val arrayAdapter3 = AccountCustomBaseAdapter(context!!, R.layout.list_item_follow, followList3)

        followListView1.adapter = arrayAdapter1
        // followListView2.adapter = arrayAdapter2
        // followListView3.adapter = arrayAdapter3

        return view
    }
}