package com.example.local_syogi.presentation.presenter

import com.example.local_syogi.domain.AuthenticationUseCase
import com.example.local_syogi.presentation.contact.SettingAccountContact

class SettingAccountPresenter(private val view: SettingAccountContact.View, private val firebase:AuthenticationUseCase) :
    SettingAccountContact.Presenter {


}