package com.example.local_syogi.presentation.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.syogibase.data.game.GameSetting
import com.example.local_syogi.syogibase.presentation.view.GameSettingSharedPreferences

/**
 * ゲーム選択画面　＞　詳細設定Fragment
 */

class SettingDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view2 = inflater.inflate(R.layout.fragment_setting_details_fragment, container, false)
        val blackButton1 = view2.findViewById<RadioButton>(R.id.blackRadioButton1)
        val blackButton2 = view2.findViewById<RadioButton>(R.id.blackRadioButton2)
        val blackButton3 = view2.findViewById<RadioButton>(R.id.blackRadioButton3)
        val blackButton4 = view2.findViewById<RadioButton>(R.id.blackRadioButton4)
        val blackButton5 = view2.findViewById<RadioButton>(R.id.blackRadioButton5)
        val blackButton6 = view2.findViewById<RadioButton>(R.id.blackRadioButton6)
        val blackButton7 = view2.findViewById<RadioButton>(R.id.blackRadioButton7)
        val blackButton8 = view2.findViewById<RadioButton>(R.id.blackRadioButton8)
        val whiteButton1 = view2.findViewById<RadioButton>(R.id.whiteRadioButton1)
        val whiteButton2 = view2.findViewById<RadioButton>(R.id.whiteRadioButton2)
        val whiteButton3 = view2.findViewById<RadioButton>(R.id.whiteRadioButton3)
        val whiteButton4 = view2.findViewById<RadioButton>(R.id.whiteRadioButton4)
        val whiteButton5 = view2.findViewById<RadioButton>(R.id.whiteRadioButton5)
        val whiteButton6 = view2.findViewById<RadioButton>(R.id.whiteRadioButton6)
        val whiteButton7 = view2.findViewById<RadioButton>(R.id.whiteRadioButton7)
        val whiteButton8 = view2.findViewById<RadioButton>(R.id.whiteRadioButton8)
        val sharedPreferences = GameSettingSharedPreferences(context!!)
        val buttonList1 = arrayListOf<RadioButton>()
        val buttonList2 = arrayListOf<RadioButton>()

        buttonList1.add(blackButton1)
        buttonList1.add(blackButton2)
        buttonList1.add(blackButton3)
        buttonList1.add(blackButton4)
        buttonList1.add(blackButton5)
        buttonList1.add(blackButton6)
        buttonList1.add(blackButton7)
        buttonList1.add(blackButton8)
        buttonList2.add(whiteButton1)
        buttonList2.add(whiteButton2)
        buttonList2.add(whiteButton3)
        buttonList2.add(whiteButton4)
        buttonList2.add(whiteButton5)
        buttonList2.add(whiteButton6)
        buttonList2.add(whiteButton7)
        buttonList2.add(whiteButton8)

        // 詳細設定画面の初期設定
        when (sharedPreferences.getHandyBlack()) {
            1 -> {
                blackButton1.isChecked = true
                GameSetting.handiBlack = 1
            }
            2 -> {
                blackButton2.isChecked = true
                GameSetting.handiBlack = 2
            }
            3 -> {
                blackButton3.isChecked = true
                GameSetting.handiBlack = 3
            }
            4 -> {
                blackButton4.isChecked = true
                GameSetting.handiBlack = 4
            }
            5 -> {
                blackButton5.isChecked = true
                GameSetting.handiBlack = 5
            }
            6 -> {
                blackButton6.isChecked = true
                GameSetting.handiBlack = 6
            }
            7 -> {
                blackButton7.isChecked = true
                GameSetting.handiBlack = 7
            }
            8 -> {
                blackButton8.isChecked = true
                GameSetting.handiBlack = 8
            }
        }
        when (sharedPreferences.getHandyWhite()) {
            1 -> {
                whiteButton1.isChecked = true
                GameSetting.handiWhite = 1
            }
            2 -> {
                whiteButton2.isChecked = true
                GameSetting.handiWhite = 2
            }
            3 -> {
                whiteButton3.isChecked = true
                GameSetting.handiWhite = 3
            }
            4 -> {
                whiteButton4.isChecked = true
                GameSetting.handiWhite = 4
            }
            5 -> {
                whiteButton5.isChecked = true
                GameSetting.handiWhite = 5
            }
            6 -> {
                whiteButton6.isChecked = true
                GameSetting.handiWhite = 6
            }
            7 -> {
                whiteButton7.isChecked = true
                GameSetting.handiWhite = 7
            }
            8 -> {
                whiteButton8.isChecked = true
                GameSetting.handiWhite = 8
            }
        }

        // ラジオボタン変更時のイベント
        buttonList1.forEach { button ->
            button.setOnCheckedChangeListener { _, _ ->
                if (button.isChecked) {
                    buttonList1.filterNot { it == button }.forEach { btn ->
                        btn.isChecked = false
                    }
                    val id: String = context!!.resources.getResourceEntryName(button.id).toString()
                    val mode = id.substring(id.length - 1).toInt()
                    GameSetting.handiBlack = mode
                    sharedPreferences.setHandyBlack(mode)
                }
            }
        }
        buttonList2.forEach { button ->
            button.setOnCheckedChangeListener { _, _ ->
                if (button.isChecked) {
                    buttonList2.filterNot { it == button }.forEach { btn ->
                        btn.isChecked = false
                    }
                    val id: String = context!!.resources.getResourceEntryName(button.id).toString()
                    val mode = id.substring(id.length - 1).toInt()
                    GameSetting.handiWhite = mode
                    sharedPreferences.setHandyWhite(mode)
                }
            }
        }
        return view2
    }

    companion object {

        @JvmStatic
        fun newInstance(): SettingDetailsFragment {
            val fragment = SettingDetailsFragment()
            return fragment
        }
    }
}