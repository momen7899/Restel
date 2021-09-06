package com.momen.data.mapper

import com.momen.data.entity.CustomerEntity
import com.momen.domain.model.Customer
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CustomerEntityDataMapperTest {


    private lateinit var mapper: CustomerEntityDataMapper
    private lateinit var customerEntity: CustomerEntity
    private lateinit var customer: Customer

    @Before
    fun setUp() {
        mapper = CustomerEntityDataMapper()
        customerEntity = CustomerEntity(1, "test", "test", "test", false, single = false)
        customer = Customer(1, "test", "test", "test", false, single = false)
    }

    @Test
    fun transformCustomerEntitiesToCustomers() {
        val list = ArrayList<CustomerEntity>()
        list.add(customerEntity)
        list.add(customerEntity)
        list.add(customerEntity)

        val actualList = mapper.transformCustomerEntitiesToCustomers(list)
        val expectList = ArrayList<Customer>()
        expectList.add(customer)
        expectList.add(customer)
        expectList.add(customer)
        Assert.assertEquals(expectList, actualList)
    }

    @Test
    fun transformCustomerToCustomerEntity() {
        val customerEntity = mapper.transformCustomerToCustomerEntity(customer)
        Assert.assertEquals(this.customerEntity, customerEntity)
    }

    @Test
    fun transformCustomerEntityToCustomer() {
        val customer = mapper.transformCustomerEntityToCustomer(customerEntity)
        Assert.assertEquals(this.customer, customer)
    }

    @Test
    fun transformCustomerEntityToCustomerNull() {
        val customer = mapper.transformCustomerEntityToCustomer(null)
        Assert.assertEquals(null, customer)
    }

}