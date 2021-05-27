package com.momen.restel.client.di

import com.momen.restel.app.AppComponent
import com.momen.restel.app.RoomDbModule
import com.momen.restel.client.view.ClientsFragment
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [ClientModule::class, RoomDbModule::class]
)
interface ClientComponent {
    fun inject(clientsFragment: ClientsFragment)
}