package com.momen.data.repository.datasource.home

import javax.inject.Inject

class HomeDataSourceFactory @Inject constructor(private val remoteHomeDatabase: RemoteHomeDatabase) {
    fun create(): HomeDataSource = remoteHomeDatabase
}