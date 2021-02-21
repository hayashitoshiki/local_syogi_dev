package com.example.local_syogi.presentation.view.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.record.GameRecordListContact
import com.example.local_syogi.syogibase.domain.model.GameModel
import kotlinx.android.synthetic.main.fragment_list_record.*
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
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val gameList1: List<GameModel> = presenter.getOnlineGameList(mode)
        val gameList2: List<GameModel> = presenter.getOfflineGameList(mode)
        val arrayAdapter1 = CustomBaseAdapter(context!!, android.R.layout.simple_list_item_1, gameList1)
        val arrayAdapter2 = CustomBaseAdapter(context!!, android.R.layout.simple_list_item_1, gameList2)

        titleTextView.text = title
        onlinListView.adapter = arrayAdapter1
        offlinListView.adapter = arrayAdapter2
        onlineMatchEdit.text = gameList1.size.toString()
        offlineMatchEdit.text = gameList2.size.toString()

        // 項目をタップしたら感想戦画面を開く
        onlinListView.setOnItemClickListener { parent, view, position, id ->
            val gameTitle = gameList1[position].title
            val gameDetail = presenter.getRecordSettingByTitle(gameTitle)
            val log = presenter.getRecordByTitle(gameTitle)
            (parentFragment as GameRecordRootFragment).setRePlayView(log, gameDetail)
        }
        offlinListView.setOnItemClickListener { parent, view, position, id ->
            val gameTitle = gameList2[position].title
            val gameDetail = presenter.getRecordSettingByTitle(gameTitle)
            val log = presenter.getRecordByTitle(gameTitle)
            (parentFragment as GameRecordRootFragment).setRePlayView(log, gameDetail)
        }
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
