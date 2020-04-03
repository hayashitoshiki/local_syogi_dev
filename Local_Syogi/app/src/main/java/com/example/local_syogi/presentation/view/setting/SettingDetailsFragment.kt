package com.example.local_syogi.presentation.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.syogibase.data.game.GameSetting

class SettingDetailsFragment : Fragment() {

    private lateinit var listView: ListView
    private val buttonList1 = arrayListOf<RadioButton>()
    private val buttonList2 = arrayListOf<RadioButton>()

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

        // ラジオボタン変更時のイベント
        blackButton1.setOnCheckedChangeListener { _, _ ->
            if (blackButton1.isChecked) {
                checkRadioButton(blackButton1)
                GameSetting.handiBlack = 1
            }
        }
        blackButton2.setOnCheckedChangeListener { _, _ ->
            if (blackButton2.isChecked) {
                checkRadioButton(blackButton2)
                GameSetting.handiBlack = 2
            }
        }
        blackButton3.setOnCheckedChangeListener { _, _ ->
            if (blackButton3.isChecked) {
                checkRadioButton(blackButton3)
                GameSetting.handiBlack = 3
            }
        }
        blackButton4.setOnCheckedChangeListener { _, _ ->
            if (blackButton4.isChecked) {
                checkRadioButton(blackButton4)
                GameSetting.handiBlack = 4
            }
        }
        blackButton5.setOnCheckedChangeListener { _, _ ->
            if (blackButton5.isChecked) {
                checkRadioButton(blackButton5)
                GameSetting.handiBlack = 5
            }
        }
        blackButton6.setOnCheckedChangeListener { _, _ ->
            if (blackButton6.isChecked) {
                checkRadioButton(blackButton6)
                GameSetting.handiBlack = 6
            }
        }
        blackButton7.setOnCheckedChangeListener { _, _ ->
            if (blackButton7.isChecked) {
                checkRadioButton(blackButton7)
                GameSetting.handiBlack = 7
            }
        }
        blackButton8.setOnCheckedChangeListener { _, _ ->
            if (blackButton8.isChecked) {
                checkRadioButton(blackButton8)
                GameSetting.handiBlack = 8
            }
        }
        whiteButton1.setOnCheckedChangeListener { _, _ ->
            if (whiteButton1.isChecked) {
                checkRadioButton2(whiteButton1)
                GameSetting.handiWhite = 1
            }
        }
        whiteButton2.setOnCheckedChangeListener { _, _ ->
            if (whiteButton2.isChecked) {
                checkRadioButton2(whiteButton2)
                GameSetting.handiWhite = 2
            }
        }
        whiteButton3.setOnCheckedChangeListener { _, _ ->
            if (whiteButton3.isChecked) {
                checkRadioButton2(whiteButton3)
                GameSetting.handiWhite = 3
            }
        }
        whiteButton4.setOnCheckedChangeListener { _, _ ->
            if (whiteButton4.isChecked) {
                checkRadioButton2(whiteButton4)
                GameSetting.handiWhite = 4
            }
        }
        whiteButton5.setOnCheckedChangeListener { _, _ ->
            if (whiteButton5.isChecked) {
                checkRadioButton2(whiteButton5)
                GameSetting.handiWhite = 5
            }
        }
        whiteButton6.setOnCheckedChangeListener { _, _ ->
            if (whiteButton6.isChecked) {
                checkRadioButton2(whiteButton6)
                GameSetting.handiWhite = 6
            }
        }
        whiteButton7.setOnCheckedChangeListener { _, _ ->
            if (whiteButton7.isChecked) {
                checkRadioButton2(whiteButton7)
                GameSetting.handiWhite = 7
            }
        }
        whiteButton8.setOnCheckedChangeListener { _, _ ->
            if (whiteButton8.isChecked) {
                checkRadioButton2(whiteButton8)
                GameSetting.handiWhite = 8
            }
        }
        return view2
    }

    // ラジオボタンの疑似的グループ化
    private fun checkRadioButton(radioButton: RadioButton) {
        buttonList1.forEach {
            if (it != radioButton) {
                it.isChecked = false
            }
        }
    }
    // ラジオボタンの疑似的グループ化
    private fun checkRadioButton2(radioButton: RadioButton) {
        buttonList2.forEach {
            if (it != radioButton) {
                it.isChecked = false
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(): SettingDetailsFragment {
            val fragment = SettingDetailsFragment()
            return fragment
        }
    }
}