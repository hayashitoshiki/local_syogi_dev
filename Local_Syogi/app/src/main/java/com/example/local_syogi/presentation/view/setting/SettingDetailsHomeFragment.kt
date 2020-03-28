package com.example.local_syogi.presentation.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.local_syogi.R

class SettingDetailsHomeFragment : Fragment(){

    private lateinit var listView: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view2 =  inflater.inflate(R.layout.fragment_setting_details_home_fragment, container, false)
        return view2
    }


    companion object {

        @JvmStatic
        fun newInstance(): SettingDetailsHomeFragment {
            val fragment = SettingDetailsHomeFragment()
            return fragment
        }
    }
}
