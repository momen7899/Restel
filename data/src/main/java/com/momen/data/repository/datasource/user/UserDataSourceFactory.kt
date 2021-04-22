package com.momen.data.repository.datasource.user

import javax.inject.Inject

class UserDataSourceFactory @Inject constructor(private val remoteUserDatabase: RemoteUserDatabase) {
    fun create(): UserDataSource = remoteUserDatabase
}