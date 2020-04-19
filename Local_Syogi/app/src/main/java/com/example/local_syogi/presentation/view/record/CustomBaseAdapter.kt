package com.example.local_syogi.presentation.view.record

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.local_syogi.R
import com.example.local_syogi.syogibase.domain.model.GameModel

/*
 *CustomListViewの生成用クラス
 */

internal class CustomBaseAdapter(context: Context?, private val resourcedId: Int, private val items: List<GameModel>) : BaseAdapter() {
    private val inflater: LayoutInflater
    private lateinit var holder: ViewHolder

    internal class ViewHolder {
        var blackResultTextView: TextView? = null
        var whiteResultTextView: TextView? = null
        var blackAccountTextView: TextView? = null
        var whiteAccountTextView: TextView? = null
        var turnCountTextView: TextView? = null
        var winTypeTextView: TextView? = null
        var titleTextView: TextView? = null
    }

    init {
        this.inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    // Viewの生成
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        if (view == null) {
            view = inflater.inflate(R.layout.game_list_item, null)
            holder = ViewHolder()
        } else {
            holder = view.tag as ViewHolder
        }

        holder.blackAccountTextView = view?.findViewById(R.id.blackAccountTextView)
        holder.whiteAccountTextView = view?.findViewById(R.id.whiteAccountTextView)
        holder.blackResultTextView = view?.findViewById(R.id.BlackResultTextView)
        holder.whiteResultTextView = view?.findViewById(R.id.WhiteResultTextView)
        holder.turnCountTextView = view?.findViewById(R.id.turnCountTextView)
        holder.winTypeTextView = view?.findViewById(R.id.winTypeTextView)
        holder.titleTextView = view?.findViewById(R.id.titleTextView)

        holder.blackAccountTextView?.text = items[position].blackName
        holder.whiteAccountTextView?.text = items[position].whiteName
        holder.turnCountTextView?.text = items[position].turnCount.toString() + "手"
        holder.winTypeTextView?.text = changeTurnCountToString(items[position].winType)
        holder.titleTextView?.text = items[position].title
        view!!.tag = holder
        when (items[position].winner) {
            1 -> {
                holder.blackResultTextView?.text = "〇"
                holder.whiteResultTextView?.text = "●"
            }
            2 -> {
            holder.blackResultTextView?.text = "●"
            holder.whiteResultTextView?.text = "〇"
            }
            else -> {
                holder.blackResultTextView?.text = "△"
                holder.whiteResultTextView?.text = "△"
            }
    }
        return view
    }

    private fun changeTurnCountToString(turnCount: Int): String {
        return when (turnCount) {
            0 -> "引き分け"
            1 -> "詰み"
            2 -> "投了"
            3 -> "接続切れ"
            4 -> "時間切れ"
            5 -> "トライ"
            6 -> "特殊"
            else -> "不正データ"
        }
    }

    // ListViewの数
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}