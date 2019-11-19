package com.example.materialdesignquicklist.model

/*
 * Google推奨マテリアルデザイン配色保持用Repository
 */

interface ColorRepository {

    //メインカラー初期化
    fun createMainColor():ArrayList<String>
    //メインカラー初期化
    fun createSubColor():ArrayList<String>
    //アクセントカラー初期化
    fun createAccentColor():ArrayList<String>

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