package com.momen.restel.main.di

import com.momen.restel.app.AppComponent
import com.momen.restel.app.RoomDbModule
import com.momen.restel.main.view.MainFragment
import dagger.Component


@Component(
    dependencies = [AppComponent::class],
    modules = [ReserveModule::class, RoomDbModule::class]
)
interface MainComponent {
    fun inject(mainFragment: MainFragment)
}