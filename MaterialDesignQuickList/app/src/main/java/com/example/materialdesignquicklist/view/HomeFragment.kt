package com.example.materialdesignquicklist.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.materialdesignquicklist.contact.HomeContact

import com.example.materialdesignquicklist.R
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf



class HomeFragment : Fragment(), HomeContact.View {

    private val presenter: HomeContact.Presenter by inject { parametersOf(this) }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val spinner1 = view.findViewById<Spinner>(R.id.main_color_spinner)
        val spinner2 = view.findViewById<Spinner>(R.id.sub_color_spinner)
        val spinner3 = view.findViewById<Spinner>(R.id.accent_color_spinner)
        val mainColorTextView   = view.findViewById<TextView>(R.id.mainColorText)
        val subColorTextView    = view.findViewById<TextView>(R.id.subColorText)
        val accentColorTextView = view.findViewById<TextView>(R.id.accentColorText)
        val mainColorContent   = view.findViewById<LinearLayout>(R.id.main_layout)
        val subColorContent    = view.findViewById<TextView>(R.id.title)
        val accentColorContent = view.findViewById<Button>(R.id.button2)

        val adapter1 = CustomAdapter(context, android.R.layout.simple_spinner_item, presenter.getMainColor())
        val adapter2 = CustomAdapter(context, android.R.layout.simple_spinner_item, presenter.getSubColor())
        val adapter3 = CustomAdapter(context, android.R.layout.simple_spinner_item, presenter.getAccentColor())

        spinner1.adapter = adapter1
        spinner2.adapter = adapter2
        spinner3.adapter = adapter3

        mainColorContent.setBackgroundColor(Color.parseColor(spinner1.selectedItem.toString()))
        subColorContent.setBackgroundColor(Color.parseColor(spinner2.selectedItem.toString()))
        accentColorContent.setBackgroundColor(Color.parseColor(spinner3.selectedItem.toString()))

        //メインカラー変更
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent!!.selectedItem as String
                mainColorContent.setBackgroundColor(Color.parseColor(item))
                presenter.changeAccentColorItems(item)
                presenter.changeSubColorList(item)
                adapter2.notifyDataSetChanged()
                adapter3.notifyDataSetChanged()
                subColorContent.setBackgroundColor(Color.parseColor(spinner2.selectedItem.toString()))
                accentColorContent.setBackgroundColor(Color.parseColor(spinner3.selectedItem.toString()))
                mainColorTextView.text = item
                subColorTextView.text = spinner2.selectedItem.toString()
                accentColorTextView.text = spinner3.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        //サブカラー変更
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent!!.selectedItem as String
                subColorContent.setBackgroundColor(Color.parseColor(item))
                subColorTextView.text = spinner2.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        //アクセントカラー変更
        spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent!!.selectedItem as String
                accentColorContent.setBackgroundColor(Color.parseColor(item))
                accentColorTextView.text = spinner3.selectedItem.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        return view
    }


}