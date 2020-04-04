package com.example.local_syogi.syogibase.data.local

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class GameEntity : RealmObject() {
    @PrimaryKey
    open var title: String? = "yyyy/MM/dd-HH:mm:ss"
    @Required
    open var winner: Int? = 0 // 0:引き分け,1:先手勝利、２：後手勝利
    @Required
    open var mode: Int? = 0
    @Required
    open var handyBlack: Int? = 0 // 先手の駒落ち
    @Required
    open var handyWhite: Int? = 0 // 後手の駒落ち
}