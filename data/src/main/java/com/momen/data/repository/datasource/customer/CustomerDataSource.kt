package com.momen.data.repository.datasource.customer

import com.momen.data.entity.CustomerEntity
import io.reactivex.Single

interface CustomerDataSource {
    fun addCustomer(customerEntity: CustomerEntity): Single<Long>?

    fun editCustomer(customerEntity: CustomerEntity): Single<CustomerEntity>?

    fun getCustomer(id: Int): Single<CustomerEntity>?

    fun getCustomers(id: Int): Single<List<CustomerEntity>>?

    fun removeCustomer(id: Int): Single<Int>?
}