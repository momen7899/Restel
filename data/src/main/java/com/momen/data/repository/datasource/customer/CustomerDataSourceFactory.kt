package com.momen.data.repository.datasource.customer

import com.momen.data.repository.datasource.user.RemoteUserDatabase
import com.momen.data.repository.datasource.user.UserDataSource
import javax.inject.Inject

class CustomerDataSourceFactory @Inject constructor(private val remoteCustomerDatabase: RemoteCustomerDatabase) {
    fun create(): CustomerDataSource = remoteCustomerDatabase
}