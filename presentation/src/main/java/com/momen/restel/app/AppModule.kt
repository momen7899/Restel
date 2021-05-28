package com.momen.restel.app

import android.content.Context
import com.momen.data.executor.JobExecutor
import com.momen.domain.executor.PostExecutionThread
import com.momen.domain.executor.ThreadExecutor
import com.momen.restel.UiThread
import dagger.Module
import dagger.Provides


@Module
class AppModule(private val context: Context) {
    @Provides
    internal fun provideContext() = context

    @Provides
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor = jobExecutor

    @Provides
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread = uiThread

}
