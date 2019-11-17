package com.example.materialdesignquicklist.presenter

import com.example.materialdesignquicklist.contact.HomeContact
import com.example.materialdesignquicklist.model.ColorRepository

class HomePresenter(private val view: HomeContact.View, private val repository: ColorRepository) : HomeContact.Presenter {


    //メインカラーを返す
    override fun getMainColor():ArrayList<String>{
        return repository.getMainColor()
    }

    //サブカラーを返す
    override fun getSubColor():ArrayList<String>{
        return repository.getSubColor()
    }

    //アクセントカラーを返す
    override fun getAccentColor():ArrayList<String>{
        return repository.getAccentColor()
    }

    //サブカラーリスト変更
    override fun changeSubColorList(selectedMainColor:String){
        repository.changeSubColorList(selectedMainColor)
    }
    //アクセントカラーリスト変更
    override fun changeAccentColorItems(selectedMainColor:String){
        repository.changeAccentColorList(selectedMainColor)
    }

}