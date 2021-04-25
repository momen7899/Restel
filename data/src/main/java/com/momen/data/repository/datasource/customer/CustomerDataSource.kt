package com.momen.data.repository.datasource.customer

import com.momen.data.entity.CustomerEntity
import io.reactivex.Single

interface CustomerDataSource {
    fun addCustomer(customerEntity: CustomerEntity): Single<Long>?

    fun editCustomer(customerEntity: CustomerEntity): Single<Int>?

    fun getCustomer(id: Int): Single<CustomerEntity>?

    fun getCustomers(): Single<List<CustomerEntity>>?

    fun removeCustomer(customerEntity: CustomerEntity): Single<Int>?
}