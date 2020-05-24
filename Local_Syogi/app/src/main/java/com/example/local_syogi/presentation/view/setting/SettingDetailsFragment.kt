package com.example.local_syogi.presentation.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
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
        val blackSpinner1 = view2.findViewById<Spinner>(R.id.blackSpinner1)
        val blackSpinner2 = view2.findViewById<Spinner>(R.id.blackSpinner2)
        val whiteSpinner1 = view2.findViewById<Spinner>(R.id.whiteSpinner1)
        val whiteSpinner2 = view2.findViewById<Spinner>(R.id.whiteSpinner2)
        val sharedPreferences = GameSettingSharedPreferences(context!!)
        val trySwitch = view2.findViewById<Switch>(R.id.trySwitch)
        val handyBlackList = arrayListOf<RadioButton>().apply {
            this.add(blackButton1)
            this.add(blackButton2)
            this.add(blackButton3)
            this.add(blackButton4)
            this.add(blackButton5)
            this.add(blackButton6)
            this.add(blackButton7)
            this.add(blackButton8)
        }
        val handyWhiteList = arrayListOf<RadioButton>().apply {
            this.add(whiteButton1)
            this.add(whiteButton2)
            this.add(whiteButton3)
            this.add(whiteButton4)
            this.add(whiteButton5)
            this.add(whiteButton6)
            this.add(whiteButton7)
            this.add(whiteButton8)
        }

        // 詳細設定画面の初期設定
        // トライルルール
        trySwitch.isChecked = sharedPreferences.getTryRule()
        // 持ち時間
        blackSpinner1.setSelection(sharedPreferences.getMinuteBlack())
        blackSpinner2.setSelection(sharedPreferences.getSecondBlack())
        whiteSpinner1.setSelection(sharedPreferences.getMinuteWhite())
        whiteSpinner2.setSelection(sharedPreferences.getSecondWhite())
        // 駒落ち
        when (sharedPreferences.getHandyBlack()) {
            1 -> blackButton1.isChecked = true
            2 -> blackButton2.isChecked = true
            3 -> blackButton3.isChecked = true
            4 -> blackButton4.isChecked = true
            5 -> blackButton5.isChecked = true
            6 -> blackButton6.isChecked = true
            7 -> blackButton7.isChecked = true
            8 -> blackButton8.isChecked = true
        }
        when (sharedPreferences.getHandyWhite()) {
            1 -> whiteButton1.isChecked = true
            2 -> whiteButton2.isChecked = true
            3 -> whiteButton3.isChecked = true
            4 -> whiteButton4.isChecked = true
            5 -> whiteButton5.isChecked = true
            6 -> whiteButton6.isChecked = true
            7 -> whiteButton7.isChecked = true
            8 -> whiteButton8.isChecked = true
        }

        // トライルール変更時イベント
        trySwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            sharedPreferences.setTryRule(isChecked)
        }
        // 持ち時間変更時イベント
        blackSpinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                val minute = item.toInt()
                sharedPreferences.setMinuteBlack(minute)
            }
        }
        blackSpinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                val second = item.toInt()
                sharedPreferences.setSecondBlack(second)
            }
        }
        whiteSpinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                val minute = item.toInt()
                sharedPreferences.setMinuteWhite(minute)
            }
        }
        whiteSpinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as String
                val second = item.toInt()
                sharedPreferences.setSecondWhite(second)
            }
        }

        // 駒落ち設定変更時のイベント
        handyBlackList.forEach { button ->
            button.setOnCheckedChangeListener { _, _ ->
                if (button.isChecked) {
                    handyBlackList.filterNot { it == button }.forEach { btn ->
                        btn.isChecked = false
                    }
                    val id: String = context!!.resources.getResourceEntryName(button.id).toString()
                    val mode = id.substring(id.length - 1).toInt()
                    sharedPreferences.setHandyBlack(mode)
                }
            }
        }
        handyWhiteList.forEach { button ->
            button.setOnCheckedChangeListener { _, _ ->
                if (button.isChecked) {
                    handyWhiteList.filterNot { it == button }.forEach { btn ->
                        btn.isChecked = false
                    }
                    val id: String = context!!.resources.getResourceEntryName(button.id).toString()
                    val mode = id.substring(id.length - 1).toInt()
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