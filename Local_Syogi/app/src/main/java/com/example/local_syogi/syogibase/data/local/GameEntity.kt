package com.example.local_syogi.syogibase.data.local

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class GameEntity: RealmObject() {
    @PrimaryKey
    open var title: String? = "yyyy/MM/dd-HH:mm:ss"
    @Required
    open var mode:Int? = 0
}