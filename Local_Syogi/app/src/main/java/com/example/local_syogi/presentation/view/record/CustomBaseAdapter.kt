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

internal class CustomBaseAdapter(context: Context?, private val resourcedId: Int, private val items: MutableList<GameModel>) : BaseAdapter() {
    private val inflater: LayoutInflater
    private lateinit var holder: ViewHolder

    internal class ViewHolder {
        var BlackResultTextView: TextView? = null
        var titleTextView: TextView? = null
        var WhiteResultTextView: TextView? = null
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

        holder.BlackResultTextView = view?.findViewById(R.id.BlackResultTextView)
        holder.titleTextView = view?.findViewById(R.id.titleTextView)
        holder.WhiteResultTextView = view?.findViewById(R.id.WhiteResultTextView)
        holder.titleTextView?.text = items[position].title
        view!!.tag = holder
        when (items[position].winner) {
            1 -> {
                holder.BlackResultTextView?.text = "〇"
                holder.WhiteResultTextView?.text = "●"
            }
            2 -> {
            holder.BlackResultTextView?.text = "●"
            holder.WhiteResultTextView?.text = "〇"
            }
            else -> {
                holder.BlackResultTextView?.text = "△"
                holder.WhiteResultTextView?.text = "△"
            }
    }
        return view
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