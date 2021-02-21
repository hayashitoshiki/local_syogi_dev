package com.example.local_syogi.syogibase.data.entity.local

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class GameEntity : RealmObject() {
    @PrimaryKey
    open var title: String? = "yyyy/MM/dd-HH:mm:ss"
    @Required
    open var blackName: String? = "先手" // 先手のユーザー名
    @Required
    open var whiteName: String? = "後手" // 後手のユーザー名
    @Required
    open var winner: Int? = 0 // 0:引き分け,1:先手勝利、２：後手勝利
    @Required
    open var winType: Int? = 0 // 勝利方法 0:引き分け,1:詰み、２:投了、３:接続切れ、４：時間切れ、５:トライ、６:特殊
    @Required
    open var type: Int? = 0 // 対戦方法(オフライン or オンライン)
    @Required
    open var mode: Int? = 0 // ゲームモード
    @Required
    open var turnCount: Int? = 0 // 終了手数
    @Required
    open var handyBlack: Int? = 0 // 先手の駒落ち
    @Required
    open var handyWhite: Int? = 0 // 後手の駒落ち
}
