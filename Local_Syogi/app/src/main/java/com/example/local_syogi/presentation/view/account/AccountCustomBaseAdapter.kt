package com.example.local_syogi.presentation.view.account

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import com.example.local_syogi.R
import com.example.local_syogi.domain.model.FollowModel

class AccountCustomBaseAdapter(context: Context?, private val resourcedId: Int, private val items: List<FollowModel>) : BaseAdapter() {
    private val inflater: LayoutInflater
    private lateinit var holder: ViewHolder

    internal class ViewHolder {
        var userNameTextView: TextView? = null
        var deleteButton: Button? = null
    }

    init {
        this.inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    // Viewの生成
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_follow, null)
            holder = ViewHolder()
        } else {
            holder = view.tag as ViewHolder
        }

        holder.userNameTextView = view?.findViewById(R.id.userNameTextView)
        holder.deleteButton = view?.findViewById(R.id.deleteButton)

        holder.userNameTextView?.text = items[position].userName

        holder.deleteButton?.text = getButtonName(items[position].status)
        if (items[position].status % 10 == 0) {
            holder.deleteButton?.visibility = View.GONE
            holder.userNameTextView?.textSize = 20F
        }
        view!!.tag = holder

        holder.deleteButton!!.setOnClickListener { view ->
            (parent as ListView).performItemClick(
                view,
                position,
                holder.deleteButton!!.id.toLong()
            )
        }

        return view
    }

    // ボタンの文字取得
    private fun getButtonName(state: Int): String {
        return when (state) {
            11 -> "削除"
            21 -> "承認"
            31 -> "取消"
            41 -> "申請"
            else -> "不正"
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