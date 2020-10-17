package com.example.local_syogi.syogibase.data.repository

import android.util.Log
import com.example.local_syogi.di.MyApplication
import com.example.local_syogi.syogibase.data.entity.game.GameLog
import com.example.local_syogi.syogibase.data.entity.game.GameMode
import com.example.local_syogi.syogibase.data.entity.local.GameEntity
import com.example.local_syogi.syogibase.data.entity.local.RecordEntity
import com.example.local_syogi.syogibase.presentation.view.GameSettingSharedPreferences
import io.realm.Realm
import io.realm.RealmConfiguration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GameRecordRepositoryImp : GameRecordRepository {
    // DBの宣言
    private lateinit var realm: Realm
    private val sharedPreferences = GameSettingSharedPreferences(MyApplication.getInstance().applicationContext)

    companion object {
        private const val TAG = "Realm"
    }

    // DB更新
    private fun updateRealm() {
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        realm = Realm.getInstance(realmConfig)
    }
    // 自動連番Id取得
    private fun getAutoIncrementIdByRecord(realm: Realm): Int {
        var nextUserId: Int = 1
        val maxUserId = realm.where(RecordEntity::class.java).max("id")
        if (maxUserId != null) {
            nextUserId = maxUserId.toInt() + 1
        }

        return nextUserId
    }

    // 新規対局データ格納
    override fun save(logList: MutableList<GameLog>, winner: Int, type: Int, blackName: String, whiteName: String, winType: Int) {
        val title = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd_HH:mm:ss"))
        updateRealm()
        val (handyBlack: Int, handyWhite: Int) = if(!GameMode.rate){
            Pair(sharedPreferences.getHandyBlack(), sharedPreferences.getHandyWhite())
        } else {
            Pair(0, 0)
        }

        realm.executeTransaction {
            val game = realm.createObject(GameEntity::class.java, title).apply{
                this.winner = winner
                this.winType = winType
                this.blackName = blackName
                this.whiteName = whiteName
                this.mode = GameMode.getModeInt()
                this.type = type
                this.handyBlack = handyBlack
                this.handyWhite = handyWhite
                this.turnCount = logList.size
            }
            realm.copyToRealm(game)
        }
        for (log in logList) {
            realm.executeTransaction {
                val record = realm.createObject(RecordEntity::class.java, getAutoIncrementIdByRecord(realm)).apply {
                    this.title = title
                    this.toX = log.oldX + 1
                    this.toY = log.oldY + 1
                    this.toPiece = log.afterPiece.nameJP
                    this.toTurn = log.afterTurn
                    this.fromX = log.newX + 1
                    this.fromY = log.newY + 1
                    this.fromPiece = log.beforpiece.nameJP
                    this.fromTurn = log.beforturn
                    this.evolution = log.evolution
                }
                realm.copyToRealm(record)
            }
        }
    }
    // 全てのモードのタイトルを取得
    override fun findTitleByAll(): Array<GameEntity> {
        updateRealm()
        val gameList = realm.where(GameEntity::class.java).findAll()
        val games: Array<GameEntity> = gameList.toTypedArray()

        return games
    }

    // 特定のモードのタイトルを取得
    override fun findTitleByMode(mode: Int): Array<GameEntity> {
        updateRealm()
        val gameList = realm.where(GameEntity::class.java).equalTo("mode", mode).findAll()
        val games: Array<GameEntity> = gameList.toTypedArray()

        return games
    }
    // 特定のタイトルの詳細を取得
    override fun findDetaileByTitle(title: String): GameEntity {
        updateRealm()
        val game = realm.where(GameEntity::class.java).equalTo("title", title).findAll().first()
        return game!!
    }

    // 特定の対局データ取得
    override fun findRecordByTitle(title: String): Array<RecordEntity> {
        val recordList = realm.where(RecordEntity::class.java).equalTo("title", title).findAll()
        val records: Array<RecordEntity> = recordList.toTypedArray()
        return if (records.isNotEmpty()) {
            records
        } else {
            Log.d(TAG, "一致する棋譜がありません")
            arrayOf(RecordEntity())
        }
    }
}