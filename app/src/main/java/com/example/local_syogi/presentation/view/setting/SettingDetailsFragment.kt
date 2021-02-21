package com.example.local_syogi.presentation.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.RadioButton
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.local_syogi.R
import com.example.local_syogi.syogibase.presentation.view.GameSettingSharedPreferences
import kotlinx.android.synthetic.main.fragment_setting_details_fragment.*

/**
 * ゲーム選択画面　＞　詳細設定Fragment
 */

class SettingDetailsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_setting_details_fragment, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sharedPreferences = GameSettingSharedPreferences(context!!)
        val handyBlackList = arrayListOf<RadioButton>().apply {
            this.add(blackRadioButton1)
            this.add(blackRadioButton2)
            this.add(blackRadioButton3)
            this.add(blackRadioButton4)
            this.add(blackRadioButton5)
            this.add(blackRadioButton6)
            this.add(blackRadioButton7)
            this.add(blackRadioButton8)
        }
        val handyWhiteList = arrayListOf<RadioButton>().apply {
            this.add(whiteRadioButton1)
            this.add(whiteRadioButton2)
            this.add(whiteRadioButton3)
            this.add(whiteRadioButton4)
            this.add(whiteRadioButton5)
            this.add(whiteRadioButton6)
            this.add(whiteRadioButton7)
            this.add(whiteRadioButton8)
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
            1 -> blackRadioButton1.isChecked = true
            2 -> blackRadioButton2.isChecked = true
            3 -> blackRadioButton3.isChecked = true
            4 -> blackRadioButton4.isChecked = true
            5 -> blackRadioButton5.isChecked = true
            6 -> blackRadioButton6.isChecked = true
            7 -> blackRadioButton7.isChecked = true
            8 -> blackRadioButton8.isChecked = true
        }
        when (sharedPreferences.getHandyWhite()) {
            1 -> whiteRadioButton1.isChecked = true
            2 -> whiteRadioButton2.isChecked = true
            3 -> whiteRadioButton3.isChecked = true
            4 -> whiteRadioButton4.isChecked = true
            5 -> whiteRadioButton5.isChecked = true
            6 -> whiteRadioButton6.isChecked = true
            7 -> whiteRadioButton7.isChecked = true
            8 -> whiteRadioButton8.isChecked = true
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
    }

    companion object {

        @JvmStatic
        fun newInstance(): SettingDetailsFragment {
            val fragment = SettingDetailsFragment()
            return fragment
        }
    }
}
