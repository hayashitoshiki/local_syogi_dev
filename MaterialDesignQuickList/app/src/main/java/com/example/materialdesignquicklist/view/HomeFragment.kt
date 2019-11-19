package com.example.materialdesignquicklist.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil

import com.example.materialdesignquicklist.R
import com.example.materialdesignquicklist.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import com.example.materialdesignquicklist.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private val mainViewModel: MainViewModel by inject { parametersOf() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding:FragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mainViewModel

        val spinner1 = binding.root.findViewById<Spinner>(R.id.main_color_spinner)
        val spinner2 = binding.root.findViewById<Spinner>(R.id.sub_color_spinner)
        val spinner3 = binding.root.findViewById<Spinner>(R.id.accent_color_spinner)
        val adapter1 = CustomAdapter(context, android.R.layout.simple_spinner_item, mainViewModel.getMainColor())
        val adapter2 = CustomAdapter(context, android.R.layout.simple_spinner_item, mainViewModel.getSubColor())
        val adapter3 = CustomAdapter(context, android.R.layout.simple_spinner_item, mainViewModel.getAccentColor())

        spinner1.adapter = adapter1
        spinner2.adapter = adapter2
        spinner3.adapter = adapter3
        spinner2.setSelection(6)
        spinner3.setSelection(8)

        //メインカラー変更
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = parent!!.selectedItem as String
                mainViewModel.onItemSelectedMainColor(position)
                mainViewModel.changeSubColor(item)
                mainViewModel.changeAccentColor(item)
                adapter2.notifyDataSetChanged()
                adapter3.notifyDataSetChanged()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        //サブカラー変更
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mainViewModel.onItemSelectedSubColor(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        //アクセントカラー変更
        spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                mainViewModel.onItemSelectedAccentColor(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        return binding.root
    }


}