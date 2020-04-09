package com.example.local_syogi.syogibase.data.local

/**
 * 棋譜格納用クラス
 *    title・・・タイトル(対局識別子)
 *    toX　・・・動かす前の場所(横)
 *    toY　・・・動かす前の場所(縦)
 *    toPiece・・動かす駒
 *    toTurn ・・動かす駒の手番
 *    fromX・・・動かす先の場所(横)
 *    fromY・・・動かす先の場所(縦)
 *    fromPiece・動かす先のマスの駒
 *    fromTurn ・動かす先のマスの手番
 *
 *   ※座標保存形式について
 *     従来の将棋と同じ形式で保存
 *     例)2八飛車　→　[2][8]飛車
 */

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class RecordEntity : RealmObject() {
    @PrimaryKey
    open var id: Int? = 0
    @Required
    open var title: String? = "yyyy/MM/dd-HH:mm:ss"
    @Required
    open var toX: Int? = 0
    @Required
    open var toY: Int? = 0
    @Required
    open var toPiece: String? = ""
    @Required
    open var toTurn: Int? = 0
    @Required
    open var fromX: Int? = 0
    @Required
    open var fromY: Int? = 0
    @Required
    open var fromPiece: String? = ""
    @Required
    open var fromTurn: Int? = 0
    @Required
    open var evolution: Boolean? = false
}