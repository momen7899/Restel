package com.momen.data.repository.reserve

import javax.inject.Inject

class ReserveDataSourceFactory @Inject constructor(private val remoteReserveDataSource: RemoteReserveDataSource) {
    fun create(): ReserveDataSource = remoteReserveDataSource
}