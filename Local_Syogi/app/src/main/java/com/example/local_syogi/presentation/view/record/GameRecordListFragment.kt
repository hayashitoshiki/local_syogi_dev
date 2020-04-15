package com.example.local_syogi.presentation.view.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.record.GameRecordListContact
import com.example.local_syogi.syogibase.domain.model.GameModel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class GameRecordListFragment : Fragment(), GameRecordListContact.View {

    private val presenter: GameRecordListContact.Presenter by inject { parametersOf(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_record, container, false)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        val onlineListView: ListView = view.findViewById(R.id.onlinListView)
        val onlineTextView: TextView = view.findViewById(R.id.onlineMatchEdit)
        val offlineListView: ListView = view.findViewById(R.id.offlinListView)
        val offlineTextView: TextView = view.findViewById(R.id.offlineMatchEdit)
        val gameList1: List<GameModel> =
            if (mode == 0) {
                presenter.getOnlineGameAll()
            } else {
                presenter.getOnlineGameByMode(mode)
            }
        val gameList2: List<GameModel> =
            if (mode == 0) {
                presenter.getOfflineGameAll()
            } else {
                presenter.getOfflineGameByMode(mode)
            }
        val arrayAdapter1 = CustomBaseAdapter(context!!, android.R.layout.simple_list_item_1, gameList1)
        val arrayAdapter2 = CustomBaseAdapter(context!!, android.R.layout.simple_list_item_1, gameList2)

        onlineListView.adapter = arrayAdapter1
        titleTextView.text = title
        if (gameList1.isNotEmpty()) {
            onlineTextView.text = gameList1.size.toString()
        }
        offlineListView.adapter = arrayAdapter2
        titleTextView.text = title
        if (gameList2.isNotEmpty()) {
            offlineTextView.text = gameList2.size.toString()
        }

        // 項目をタップしたら感想戦画面を開く
        onlineListView.setOnItemClickListener { parent, view, position, id ->
            val gameTitle = gameList1[position].title
            val gameDetail = presenter.getRecordSettingByTitle(gameTitle)
            val log = presenter.getRecordByTitle(gameTitle)
            val mFragment = parentFragment as GameRecordRootFragment
            mFragment.setRePlayView(log, gameDetail)
        }
        offlineListView.setOnItemClickListener { parent, view, position, id ->
            val gameTitle = gameList2[position].title
            val gameDetail = presenter.getRecordSettingByTitle(gameTitle)
            val log = presenter.getRecordByTitle(gameTitle)
            val mFragment = parentFragment as GameRecordRootFragment
            mFragment.setRePlayView(log, gameDetail)
        }
        return view
    }

    companion object {
        private var title: String = "タイトル"
        private var mode: Int = 0

        @JvmStatic
        fun newInstance(title: String, mode: Int): GameRecordListFragment {
            val fragment = GameRecordListFragment()
            this.title = title
            this.mode = mode
            return fragment
        }
    }
}