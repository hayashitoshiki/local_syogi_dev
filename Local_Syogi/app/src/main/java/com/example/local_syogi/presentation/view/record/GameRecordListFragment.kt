package com.example.local_syogi.presentation.view.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.presentation.contact.GameRecordListContact
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
        val offLineListView: ListView = view.findViewById(R.id.offlinListView)
        val offLineTextView: TextView = view.findViewById(R.id.offLineMatchEdit)
        val gameList: MutableList<GameModel> =
            if (mode == 0) {
                presenter.getGameAll()
            } else {
                presenter.getGameByMode(mode)
            }
        val arrayAdapter = CustomBaseAdapter(context!!, android.R.layout.simple_list_item_1, gameList)

        offLineListView.adapter = arrayAdapter
        titleTextView.text = title
        if (gameList.size != 0) {
            offLineTextView.text = gameList.size.toString()
        }

        // 項目をタップしたら感想戦画面を開く
        offLineListView.setOnItemClickListener { parent, view, position, id ->
            val gameTitle = gameList[position].title
            val log = presenter.getRecordByTitle(gameTitle)
            val mFragment = parentFragment as GameRecordRootFragment
            mFragment.setRePlayView(log)
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