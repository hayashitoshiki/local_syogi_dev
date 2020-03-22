package com.example.local_syogi.syogibase.data.local

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