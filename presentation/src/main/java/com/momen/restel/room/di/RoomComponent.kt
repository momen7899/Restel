package com.momen.restel.room.di

import com.momen.restel.app.AppComponent
import com.momen.restel.app.RoomDbModule
import com.momen.restel.room.view.RoomFragment
import dagger.Component

@Component(dependencies = [AppComponent::class], modules = [RoomModule::class, RoomDbModule::class])
interface RoomComponent {
    fun inject(roomFragment: RoomFragment)
}