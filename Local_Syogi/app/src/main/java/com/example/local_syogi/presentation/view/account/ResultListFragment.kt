package com.example.local_syogi.presentation.view.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.local_syogi.R

class ResultListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_result, container, false)
        val titleTextView: TextView = view.findViewById(R.id.titleTextView)
        titleTextView.text = title
        return view
    }

    companion object {
        private var title: String = "タイトル"

        @JvmStatic
        fun newInstance(title: String): ResultListFragment {
            val fragment = ResultListFragment()
            this.title = title
            return fragment
        }
    }
}