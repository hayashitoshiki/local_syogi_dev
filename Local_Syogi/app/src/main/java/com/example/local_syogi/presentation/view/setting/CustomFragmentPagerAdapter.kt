package com.example.local_syogi.presentation.view.setting

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import android.os.Parcelable



class CustomFragmentPagerAdapter (manager: FragmentManager): FragmentStatePagerAdapter(manager) {


    override fun getCount(): Int {
        return 2
    }

    //ページセット
    override fun getItem(position: Int): Fragment {
        Log.d("Main", "position：" + position)
        return when(position){
            0 -> SettingDetailsHomeFragment.newInstance()
            else -> SettingDetailsFragment.newInstance()
        }
    }

    override fun saveState(): Parcelable? {
        return null
    }
}