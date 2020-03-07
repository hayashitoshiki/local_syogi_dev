package com.example.local_syogi.di

import android.app.Application
import com.example.local_syogi.presentation.contact.*
import com.example.local_syogi.presentation.presenter.*
import com.example.local_syogi.syogibase.presentation.contact.GameViewContact
import com.example.local_syogi.syogibase.data.BoardRepository
import com.example.local_syogi.syogibase.data.BoardRepositoryImp
import com.example.local_syogi.syogibase.domain.SyogiLogicUseCaseImp
import com.example.local_syogi.syogibase.domain.SyogiLogicUseCase
import com.example.local_syogi.syogibase.presentation.presenter.GameLogicPresenter
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()


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
        factory <SyogiLogicUseCase>{ SyogiLogicUseCaseImp(get()) }
        factory <BoardRepository>{ BoardRepositoryImp() }

    }
}


