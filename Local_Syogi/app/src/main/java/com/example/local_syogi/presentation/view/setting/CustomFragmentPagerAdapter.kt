package com.example.local_syogi.presentation.view.setting

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/**
 * PagerAdapterの拡張クラス
 *   0→空画面セット
 *   1→詳細設定画面セット
 */

class CustomFragmentPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {

    override fun getCount(): Int {
        return 2
    }

    // ページセット
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> SettingDetailsHomeFragment.newInstance()
            else -> SettingDetailsFragment.newInstance()
        }
    }

    override fun saveState(): Parcelable? {
        return null
    }
}