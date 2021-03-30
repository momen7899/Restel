package com.momen.restel.app

import android.content.Context
import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
interface AppComponent {
    fun context(): Context

    fun threadExecutor(): ThreadExecutor

    fun postExecutionThread(): PostExecutionThread

}
