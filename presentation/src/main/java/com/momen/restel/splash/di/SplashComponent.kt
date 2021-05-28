package com.momen.restel.splash.di

import com.momen.restel.app.AppComponent
import com.momen.restel.app.RoomDbModule
import com.momen.restel.splash.view.SplashFragment
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [SplashModule::class, RoomDbModule::class]
)
interface SplashComponent {
    fun inject(splashFragment: SplashFragment)
}