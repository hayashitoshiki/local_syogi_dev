package com.example.materialdesignquicklist.view

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import com.example.materialdesignquicklist.R
import java.util.ArrayList



class CustomAdapter(context: Context?, private val resourcedId: Int, private val items: ArrayList<String>) : BaseAdapter() {

    private val inflater: LayoutInflater


    private class ViewHolder(view: View) {
        val colorBg = view.findViewById(R.id.color) as LinearLayout
    }

    init {
        this.inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view :View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_spiner,null)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        Log.d("Color","color:" + items[position])
        holder.colorBg.setBackgroundColor(Color.parseColor(items[position]))

        return view
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getCount(): Int {
        return items.size
    }
}