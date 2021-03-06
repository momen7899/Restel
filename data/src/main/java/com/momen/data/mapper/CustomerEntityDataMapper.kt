package com.momen.data.mapper

import com.momen.data.entity.CustomerEntity
import com.momen.domain.model.Customer
import javax.inject.Inject

class CustomerEntityDataMapper @Inject constructor() {
    fun transformCustomerEntitiesToCustomers(customerEntities: List<CustomerEntity>?): ArrayList<Customer> {
        val customers = ArrayList<Customer>()
        customerEntities?.let {
            for (customer in it)
                transformCustomerEntityToCustomer(customer)?.let { item -> customers.add(item) }
        }
        return customers
    }

    fun transformCustomerToCustomerEntity(customer: Customer): CustomerEntity =
        with(customer) {
            CustomerEntity(id, name, phoneNumber, nationalCode, gender, single)
        }

    fun transformCustomerEntityToCustomer(customer: CustomerEntity?): Customer? =
        customer?.let {
            Customer(it.id, it.name, it.phoneNumber, it.nationalCode, it.gender, it.single)
        }

}