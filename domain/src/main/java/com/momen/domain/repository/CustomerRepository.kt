package com.momen.domain.repository

import com.momen.domain.model.Customer
import io.reactivex.Single

interface CustomerRepository {

    fun addCustomer(customer: Customer): Single<Customer>?

    fun editCustomer(customer: Customer): Single<Customer>?

    fun getCustomers(): Single<ArrayList<Customer>>?

    fun getCustomer(id: Int): Single<Customer>?

    fun removeCustomer(id: Int): Single<Customer>?
}