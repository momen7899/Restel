package com.momen.data.repository.datasource.customer

import com.momen.data.entity.CustomerEntity
import com.momen.data.room.RestelAppDatabase
import io.reactivex.Single
import javax.inject.Inject

class RemoteCustomerDatabase @Inject constructor(private val room: RestelAppDatabase) :
    CustomerDataSource {
    override fun addCustomer(customerEntity: CustomerEntity): Single<Long>? =
        room.getDatabaseDAO()?.addCustomer(customerEntity)

    override fun editCustomer(customerEntity: CustomerEntity): Single<Int>? =
        room.getDatabaseDAO()?.editCustomer(customerEntity)

    override fun getCustomer(id: Int): Single<CustomerEntity>? =
        room.getDatabaseDAO()?.getCustomer(id)

    override fun getCustomers(): Single<List<CustomerEntity>>? =
        room.getDatabaseDAO()?.getCustomers()

    override fun removeCustomer(customerEntity: CustomerEntity): Single<Int>? =
        room.getDatabaseDAO()?.removeCustomer(customerEntity)


}