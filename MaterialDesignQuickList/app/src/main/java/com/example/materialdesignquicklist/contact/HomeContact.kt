package com.example.materialdesignquicklist.contact

interface HomeContact {
    interface View {

    }

    interface Presenter {

        //メインカラーを返す
        fun getMainColor():ArrayList<String>
        //サブカラーを返す
        fun getSubColor():ArrayList<String>
        //アクセントカラーを返す
        fun getAccentColor():ArrayList<String>

        //サブカラーリスト変更
        fun changeSubColorList(selectedMainColor:String)
        //アクセントカラーリスト変更
        fun changeAccentColorItems(selectedMainColor:String)

        }
}