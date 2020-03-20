package com.example.local_syogi.syogibase.data.repository

import android.util.Log
import com.example.local_syogi.syogibase.data.game.GameLog
import com.example.local_syogi.syogibase.data.local.GameEntity
import com.example.local_syogi.syogibase.data.local.RecordEntity
import io.realm.Realm
import io.realm.RealmConfiguration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GameRecordRepositoryImp:GameRecordRepository {
    //DBの宣言
    private lateinit var realm: Realm

    companion object {
        private const val TAG = "Realm"
    }

    //DB更新
    private fun updateRealm(){
        val realmConfig = RealmConfiguration.Builder()
            .deleteRealmIfMigrationNeeded()
            .build()
        realm = Realm.getInstance(realmConfig)
    }
    //自動連番Id取得
    private fun getAutoIncrementIdByRecord(realm:Realm): Int {
        // 初期化
        var nextUserId:Int = 1
        // userIdの最大値を取得
        val maxUserId = realm.where(RecordEntity::class.java).max("id")
        // 1度もデータが作成されていない場合はNULLが返ってくるため、NULLチェックをする
        if (maxUserId != null) {
            nextUserId = maxUserId.toInt() + 1
        }

        return nextUserId
    }

    //新規対局データ格納
    override fun save(logList:MutableList<GameLog>){
        val title = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"))
        Log.d(TAG,"現在の時間：" + title)
        updateRealm()

        realm.executeTransaction {
            val game = realm.createObject(GameEntity::class.java,title)
            game.mode  = 1
            realm.copyToRealm(game)
        }
        for (log in logList){
            realm.executeTransaction {
                val record = realm.createObject(RecordEntity::class.java ,getAutoIncrementIdByRecord(realm))
                record.title     = title
                record.toX       = log.oldX
                record.toY       = log.oldY
                record.toPiece   = log.afterPiece.nameJP
                record.toTurn    = log.afterTurn
                record.fromX     = log.newX
                record.fromY     = log.newY
                record.fromPiece = log.beforpiece.nameJP
                record.fromTurn  = log.beforturn
                record.evolution = log.evolution

                realm.copyToRealm(record)
            }
        }
    }
    //全てのモードのタイトルを取得
    override fun findTitleByAll():Array<GameEntity>{
        updateRealm()
        Log.d(TAG,"全対局取得")
        val gameList = realm.where(GameEntity::class.java).findAll()
        val games:Array<GameEntity> = gameList.toTypedArray()
        for(game in games){
            Log.d(TAG,"対戦記録・・Title：" + game.title + "選択モード:" + game.mode)
        }
        return games
    }

    //特定のモードのタイトルを取得
    override fun findTitleByMode(){}
    //特定の対局データ取得
    override fun findRecordByTitle(title:String):Array<RecordEntity>{
        val recordList = realm.where(RecordEntity::class.java).equalTo("title",title).findAll()
        val records:Array<RecordEntity> = recordList.toTypedArray()
        return if(records.isNotEmpty()){
            records
        }
        else {
            Log.d(TAG,"一致する棋譜がありません")
           arrayOf(RecordEntity())
        }
    }
}