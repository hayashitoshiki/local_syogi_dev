package com.example.materialdesignquicklist.di

import android.app.Application
import com.example.materialdesignquicklist.contact.HomeContact
import com.example.materialdesignquicklist.model.*
import com.example.materialdesignquicklist.presenter.HomePresenter
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module


class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()


        startKoin{
            androidContext(applicationContext)
            modules(module)
        }
    }

    // Koinモジュール
    private val module: Module = module  {
        factory <HomeContact.Presenter>{ (v: HomeContact.View) -> HomePresenter(v,get()) }
        factory <ColorRepository>{ ColorRepositoryImp() }
    }


}


