package com.momen.restel.reserve.di

import com.momen.restel.app.AppComponent
import com.momen.restel.app.ReserveModule
import com.momen.restel.app.RoomModule
import com.momen.restel.reserve.view.ReserveFragment
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [ReserveModule::class, RoomModule::class]
)
interface ReserveComponent {
    fun inject(reserveFragment: ReserveFragment)
}