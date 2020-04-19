package com.example.local_syogi.di

import android.app.Application
import com.example.local_syogi.data.FirebaseRepository
import com.example.local_syogi.data.FirebaseRepositoryImp
import com.example.local_syogi.domain.AuthenticationUseCase
import com.example.local_syogi.domain.AuthenticationUseCaseImp
import com.example.local_syogi.presentation.contact.account.SettingAccountContact
import com.example.local_syogi.presentation.contact.game.GamePlayBackContact
import com.example.local_syogi.presentation.contact.game.GameViewRateContact
import com.example.local_syogi.presentation.contact.record.GameRecordListContact
import com.example.local_syogi.presentation.contact.record.GameRecordRootContact
import com.example.local_syogi.presentation.contact.setting.*
import com.example.local_syogi.presentation.presenter.account.SettingAccountPresenter
import com.example.local_syogi.presentation.presenter.game.GameLogicRatePresenter
import com.example.local_syogi.presentation.presenter.game.GamePlayBackPresenter
import com.example.local_syogi.presentation.presenter.record.GameRecordListPresenter
import com.example.local_syogi.presentation.presenter.record.GameRecordRootPresenter
import com.example.local_syogi.presentation.presenter.setting.*
import com.example.local_syogi.syogibase.data.repository.BoardRepository
import com.example.local_syogi.syogibase.data.repository.BoardRepositoryImp
import com.example.local_syogi.syogibase.data.repository.GameRecordRepository
import com.example.local_syogi.syogibase.data.repository.GameRecordRepositoryImp
import com.example.local_syogi.syogibase.domain.usecase.SyogiLogicUseCase
import com.example.local_syogi.syogibase.domain.usecase.SyogiLogicUseCaseImp
import com.example.local_syogi.syogibase.presentation.contact.GameViewContact
import com.example.local_syogi.syogibase.presentation.presenter.GameLogicPresenter
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class MyApplication : Application() {

    companion object {
         private lateinit var sInstance: MyApplication

        @JvmStatic
        @Synchronized
        fun getInstance(): MyApplication {
            return sInstance
        }
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()

        startKoin {
            androidContext(applicationContext)
            modules(module)
        }
        sInstance = this
    }

    // Koinモジュール
    private val module: Module = module {
        factory <SettingSyogiBaseContact.Presenter> { (v: SettingSyogiBaseContact.View) -> SettingSyogiBasePresenter(v) }
        factory <GameViewContact.Presenter> { (v: GameViewContact.View) -> GameLogicPresenter(v, get()) }
        factory <GameViewRateContact.Presenter> { (v: GameViewRateContact.View) -> GameLogicRatePresenter(v, get(), get()) }
        factory <SettingAccountContact.Presenter> { (v: SettingAccountContact.View) -> SettingAccountPresenter(v, get()) }
        factory <SettingRootContact.Presenter> { (v: SettingRootContact.View) -> SettingRootPresenter(v, get()) }
        factory <SettingCardBaseContact.Presenter> { (v: SettingCardBaseContact.View) -> SettingCardBasePresenter(v) }
        factory <GameRecordRootContact.Presenter> { (v: GameRecordRootContact.View) -> GameRecordRootPresenter(v, get()) }
        factory <GameRecordListContact.Presenter> { (v: GameRecordListContact.View) -> GameRecordListPresenter(v, get()) }
        factory <GamePlayBackContact.Presenter> { (v: GamePlayBackContact.View) -> GamePlayBackPresenter(v) }

        factory <SyogiLogicUseCase> { SyogiLogicUseCaseImp(get(), get()) }
        factory <AuthenticationUseCase> { AuthenticationUseCaseImp(get()) }

        factory <FirebaseRepository> { FirebaseRepositoryImp() }
        factory <BoardRepository> { BoardRepositoryImp() }
        factory <GameRecordRepository> { GameRecordRepositoryImp() }
    }
}