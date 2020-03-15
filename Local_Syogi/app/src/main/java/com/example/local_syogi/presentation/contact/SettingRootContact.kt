package com.example.local_syogi.presentation.contact

interface SettingRootContact {
    interface View{

    }

    interface Presenter{
        //ログイン認証
        fun isAuth():Boolean
    }
}