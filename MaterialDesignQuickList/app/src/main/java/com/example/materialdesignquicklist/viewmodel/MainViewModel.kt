package com.example.materialdesignquicklist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.materialdesignquicklist.model.ColorRepository
import com.example.materialdesignquicklist.model.ColorRepositoryImp

class MainViewModel : ViewModel() {


    private val repository: ColorRepository = ColorRepositoryImp()

    var mainColorText:MutableLiveData<String> = MutableLiveData("#f44336")
    var subColorText:MutableLiveData<String> = MutableLiveData("#f44336")
    var accentColorText:MutableLiveData<String> = MutableLiveData("#f44336")

    private var subColorPosition:Int = 0
    private var accentColorPosition:Int = 0


    /*ーーーーーーーーーーーーーーーーーーー
             各配色リストの初期化
     ーーーーーーーーーーーーーーーーーーー*/
    fun getMainColor():ArrayList<String>{
        return repository.createMainColor()
    }
    fun getSubColor():ArrayList<String>{
        return repository.createSubColor()
    }
    fun getAccentColor():ArrayList<String>{
        return repository.createAccentColor()
    }

    /*ーーーーーーーーーーーーーーーーーーー
            各配色リストの変更
    ーーーーーーーーーーーーーーーーーーー*/
    fun changeSubColor(selectedMainColor:String){
        repository.changeSubColorList(selectedMainColor)
        subColorText.postValue(repository.getSubColor()[subColorPosition])
    }
    fun changeAccentColor(selectedMainColor:String){
        repository.changeAccentColorList(selectedMainColor)
        accentColorText.postValue(repository.getAccentColor()[accentColorPosition])
    }

    /*ーーーーーーーーーーーーーーーーーーー
            各選択配色テキストの変更
    ーーーーーーーーーーーーーーーーーーー*/
    fun onItemSelectedMainColor(position: Int) {
        mainColorText.postValue(repository.getMainColor()[position])
    }
    fun onItemSelectedSubColor(position:Int){
        subColorText.postValue(repository.getSubColor()[position])
        subColorPosition = position
    }
    fun onItemSelectedAccentColor(position:Int){
        accentColorText.postValue(repository.getAccentColor()[position])
        accentColorPosition = position
    }

}