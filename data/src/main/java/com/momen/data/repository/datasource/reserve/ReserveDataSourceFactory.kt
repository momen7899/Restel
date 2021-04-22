package com.momen.data.repository.datasource.reserve

import javax.inject.Inject

class ReserveDataSourceFactory @Inject constructor(private val remoteReserveDataSource: RemoteReserveDataSource) {
    fun create(): ReserveDataSource = remoteReserveDataSource
}