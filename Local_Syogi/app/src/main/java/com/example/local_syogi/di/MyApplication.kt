package com.example.local_syogi.di

import android.app.Application
import com.example.local_syogi.data.FirebaseRepository
import com.example.local_syogi.data.FirebaseRepositoryImp
import com.example.local_syogi.domain.AuthenticationUseCase
import com.example.local_syogi.domain.AuthenticationUseCaseImp
import com.example.local_syogi.presentation.contact.*
import com.example.local_syogi.presentation.presenter.*
import com.example.local_syogi.syogibase.presentation.contact.GameViewContact
import com.example.local_syogi.syogibase.data.repository.BoardRepository
import com.example.local_syogi.syogibase.data.repository.BoardRepositoryImp
import com.example.local_syogi.syogibase.domain.SyogiLogicUseCaseImp
import com.example.local_syogi.syogibase.domain.SyogiLogicUseCase
import com.example.local_syogi.presentation.contact.GamePlayBackContact
import com.example.local_syogi.syogibase.presentation.presenter.GameLogicPresenter
import com.example.local_syogi.presentation.presenter.GamePlayBackPresenter
import com.example.local_syogi.syogibase.data.repository.GameRecordRepository
import com.example.local_syogi.syogibase.data.repository.GameRecordRepositoryImp
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().build()

        startKoin {
            androidContext(applicationContext)
            modules(module)
        }
    }

    // Koinモジュール
    private val module: Module = module {
        factory <UsuallySyogiContact.Presenter>{ (v: UsuallySyogiContact.View) -> UsuallySyogiPresenter(v) }
        factory <AnnnanSyogiContact.Presenter>{ (v: AnnnanSyogiContact.View) -> AnnnanSyogiPresenter(v) }
        factory <SeccondSyogiContact.Presenter>{ (v: SeccondSyogiContact.View) -> SeccondSyogiPresenter(v) }
        factory <QueenSyogiContact.Presenter>{ (v: QueenSyogiContact.View) -> QueenSyogiPresenter(v) }
        factory <ChaosSyogiContact.Presenter>{ (v: ChaosSyogiContact.View) -> ChaosSyogiPresenter(v) }
        factory <CheckmateSyogiContact.Presenter>{ (v: CheckmateSyogiContact.View) -> CheckmateSyogiPresenter(v) }
        factory <PieceLimitSyogiContact.Presenter>{ (v: PieceLimitSyogiContact.View) -> PieceLimitSyogiPresenter(v) }
        factory <GameViewContact.Presenter>{ (v: GameViewContact.View) -> GameLogicPresenter(v,get()) }
        factory <GameViewRateContact.Presenter>{ (v: GameViewRateContact.View) -> GameLogicRatePresenter(v,get()) }
        factory <SettingAccountContact.Presenter>{ (v: SettingAccountContact.View) -> SettingAccountPresenter(v,get()) }
        factory <SettingRootContact.Presenter>{ (v: SettingRootContact.View) -> SettingRootPresenter(v,get()) }
        factory <RateCardContact.Presenter>{ (v: RateCardContact.View) -> RateCardPresenter(v) }
        factory <GamePlayBackContact.Presenter>{ (v: GamePlayBackContact.View) ->
            GamePlayBackPresenter(
                v
            )
        }


        factory <SyogiLogicUseCase>{ SyogiLogicUseCaseImp(get(),get()) }
        factory <AuthenticationUseCase>{ AuthenticationUseCaseImp(get())}

        factory <FirebaseRepository>{ FirebaseRepositoryImp() }
        factory <BoardRepository>{ BoardRepositoryImp() }
        factory <GameRecordRepository>{GameRecordRepositoryImp() }
    }
}


