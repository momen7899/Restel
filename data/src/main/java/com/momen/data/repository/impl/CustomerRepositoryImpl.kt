package com.momen.data.repository.impl

import com.momen.data.mapper.CustomerEntityDataMapper
import com.momen.data.repository.datasource.customer.CustomerDataSourceFactory
import com.momen.domain.model.Customer
import com.momen.domain.repository.CustomerRepository
import io.reactivex.Single

class CustomerRepositoryImpl(
    private val customerDataSourceFactory: CustomerDataSourceFactory,
    private val customerEntityDataMapper: CustomerEntityDataMapper
) : CustomerRepository {
    override fun addCustomer(customer: Customer): Single<Long>? =
        customerDataSourceFactory.create()
            .addCustomer(customerEntityDataMapper.transformCustomerToCustomerEntity(customer))

    override fun editCustomer(customer: Customer): Single<Customer>? =
        customerDataSourceFactory.create()
            .editCustomer(customerEntityDataMapper.transformCustomerToCustomerEntity(customer))
            ?.map(customerEntityDataMapper::transformCustomerEntityToCustomer)


    override fun getCustomers(): Single<ArrayList<Customer>>? =
        customerDataSourceFactory.create()
            .getCustomers()
            ?.map(customerEntityDataMapper::transformCustomerEntitiesToCustomers)

    override fun getCustomer(id: Int): Single<Customer>? =
        customerDataSourceFactory.create()
            .getCustomer(id)?.map(customerEntityDataMapper::transformCustomerEntityToCustomer)

    override fun removeCustomer(id: Int): Single<Int>? =
        customerDataSourceFactory.create()
            .removeCustomer(id)
}