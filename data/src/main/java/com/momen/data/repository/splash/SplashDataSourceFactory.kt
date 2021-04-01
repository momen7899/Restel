package com.momen.data.repository.splash

import javax.inject.Inject

class SplashDataSourceFactory @Inject constructor(
    private val remoteSplashDataSource: RemoteSplashDataSource
) {
    fun create(): SplashDataSource = remoteSplashDataSource
}
