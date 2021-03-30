package com.momen.restel.splash.di

import com.momen.restel.app.AppComponent
import com.momen.restel.app.RoomModule
import com.momen.restel.splash.view.SplashFragment
import dagger.Component

@Component(dependencies = [AppComponent::class], modules = [SplashModule::class, RoomModule::class])
interface SplashComponent {
    fun inject(splashFragment: SplashFragment)
}