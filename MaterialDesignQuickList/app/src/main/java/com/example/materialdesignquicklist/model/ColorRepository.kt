package com.example.materialdesignquicklist.model

interface ColorRepository {

    //メインカラーを返す
    fun getMainColor():ArrayList<String>
    //サブカラーを返す
    fun getSubColor():ArrayList<String>
    //アクセントカラーを返す
    fun getAccentColor():ArrayList<String>

    //サブカラーパレット変更
    fun changeSubColorList(selectedMainColor:String):ArrayList<String>
    //アクセントカラーリスト変更
    fun changeAccentColorList(selectedMainColor:String)
}