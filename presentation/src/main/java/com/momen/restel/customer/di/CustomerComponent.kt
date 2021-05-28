package com.momen.restel.customer.di

import com.momen.restel.app.AppComponent
import com.momen.restel.app.RoomDbModule
import com.momen.restel.customer.view.CustomerFragment
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [CustomerModule::class, RoomDbModule::class]
)
interface CustomerComponent {
    fun inject(customerFragment: CustomerFragment)
}