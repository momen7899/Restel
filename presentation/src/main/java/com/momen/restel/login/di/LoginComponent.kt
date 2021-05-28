package com.momen.restel.login.di

import com.momen.restel.app.AppComponent
import com.momen.restel.app.RoomDbModule
import com.momen.restel.login.view.LoginFragment
import dagger.Component


@Component(
    dependencies = [AppComponent::class],
    modules = [LoginModule::class, RoomDbModule::class]
)
interface LoginComponent {
    fun inject(loginFragment: LoginFragment)
}