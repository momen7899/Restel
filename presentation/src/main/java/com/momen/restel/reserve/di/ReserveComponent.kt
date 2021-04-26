package com.momen.restel.reserve.di

import com.momen.restel.app.AppComponent
import com.momen.restel.main.di.ReserveModule
import com.momen.restel.app.RoomDbModule
import com.momen.restel.reserve.view.ReserveFragment
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [ReserveModule::class, RoomDbModule::class]
)
interface ReserveComponent {
    fun inject(reserveFragment: ReserveFragment)
}