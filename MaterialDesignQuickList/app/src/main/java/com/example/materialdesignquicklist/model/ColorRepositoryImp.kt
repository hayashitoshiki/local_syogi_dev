package com.example.materialdesignquicklist.model


class ColorRepositoryImp: ColorRepository {


    private val color = ColorEntity()

    private var mainColorItems:ArrayList<String>   = arrayListOf()
    private var subColorItems:ArrayList<String>    = arrayListOf()
    private var accentColorItems:ArrayList<String> = arrayListOf()


    //メインカラー初期化
    override fun createMainColor():ArrayList<String>{
        mainColorItems.addAll(color.mainColor)
        return mainColorItems
    }
    //サブカラー初期化
    override fun createSubColor():ArrayList<String>{
        subColorItems.addAll(color.subColorItemsRed)
        return subColorItems
    }
    //アクセントカラー初期化
    override fun createAccentColor():ArrayList<String>{
        accentColorItems.addAll(color.mainColor)
        accentColorItems.removeAt(0)
        return accentColorItems
    }

    //メインカラーを返す
    override fun getMainColor():ArrayList<String>{
        return mainColorItems
    }
    //サブカラーを返す
    override fun getSubColor():ArrayList<String>{
        return subColorItems
    }
    //アクセントカラーを返す
    override fun getAccentColor():ArrayList<String>{
        return accentColorItems
    }

    //サブカラーパレット変更
    override fun changeSubColorList(selectedMainColor:String):ArrayList<String>{
        subColorItems.clear()
        when (selectedMainColor){
            "#f44336" -> subColorItems.addAll(color.subColorItemsRed)
            "#E91E63" -> subColorItems.addAll(color.subColorItemsPink)
            "#9C27B0" -> subColorItems.addAll(color.subColorItemsPurple)
            "#673AB7" -> subColorItems.addAll(color.subColorItemsDeepPurple)
            "#3F51B5" -> subColorItems.addAll(color.subColorItemsIndigo)
            "#2196F3" -> subColorItems.addAll(color.subColorItemsBlue)
            "#03A9F4" -> subColorItems.addAll(color.subColorItemsLightBlue)
            "#00BCD4" -> subColorItems.addAll(color.subColorItemsCyan)
            "#009688" -> subColorItems.addAll(color.subColorItemsTeal)
            "#4CAF50" -> subColorItems.addAll(color.subColorItemsGreen)
            "#8BC34A" -> subColorItems.addAll(color.subColorItemsLightGreen)
            "#CDDC39" -> subColorItems.addAll(color.subColorItemsLime)
            "#FFEB3B" -> subColorItems.addAll(color.subColorItemsYellow)
            "#FFC107" -> subColorItems.addAll(color.subColorItemsAmber)
            "#FF9800" -> subColorItems.addAll(color.subColorItemsOrange)
            "#FF5722" -> subColorItems.addAll(color.subColorItemsDeepOrange)
            "#795548" -> subColorItems.addAll(color.subColorItemsBrown)
            "#9E9E9E" -> subColorItems.addAll(color.subColorItemsGrey)
            "#607D8B" -> subColorItems.addAll(color.subColorItemsBlueGrey)
            else -> subColorItems.addAll(color.subColorItemsRed)
        }
        return subColorItems
    }
    //アクセントカラーリスト変更
    override fun changeAccentColorList(selectedMainColor:String){
        accentColorItems.clear()
        accentColorItems.addAll(color.mainColor)
        accentColorItems.remove(selectedMainColor)
    }
}