package com.momen.restel.main.di

import com.momen.restel.app.AppComponent
import com.momen.restel.app.ReserveModule
import com.momen.restel.app.RoomModule
import com.momen.restel.main.view.MainFragment
import dagger.Component


@Component(
    dependencies = [AppComponent::class],
    modules = [ReserveModule::class, RoomModule::class]
)
interface MainComponent {
    fun inject(mainFragment: MainFragment)
}