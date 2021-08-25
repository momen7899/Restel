package com.momen.data.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.momen.data.entity.CustomerEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CustomerTableTest {

    private lateinit var database: RestelAppDatabase
    private lateinit var dao: DatabaseDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RestelAppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getDatabaseDAO()!!
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCustomer() {
        val customer = createCustomer()

        dao.addCustomer(customer)?.test()

        dao.getCustomers()?.test()?.assertValue {
            it.contains(customer)
        }
    }

    @Test
    fun updateCustomer() {
        val customer = createCustomer()

        dao.addCustomer(customer)?.test()

        val editedCustomer = CustomerEntity(
            1, "edited", "edited", "edited", false, single = false
        )

        dao.editCustomer(editedCustomer)?.test()

        dao.getCustomers()?.test()?.assertValue {
            it.contains(editedCustomer) && !it.contains(customer)
        }
    }

    @Test
    fun getCustomer() {
        val customer = createCustomer()

        dao.addCustomer(customer)?.test()

        dao.getCustomer(1)?.test()?.assertValue {
            it == customer
        }
    }

    @Test
    fun getCustomers() {
        val customers = createCustomers()
        customers.forEach {
            dao.addCustomer(it)?.test()
        }

        dao.getCustomers()?.test()?.assertValue {
            it.containsAll(customers)
        }
    }

    @Test
    fun removeCustomer() {
        val customer = createCustomer()

        dao.addCustomer(customer)?.test()

        dao.removeCustomer(customer)?.test()

        dao.getCustomers()?.test()?.assertValue {
            !it.contains(customer)
        }

    }

    private fun createCustomers(): ArrayList<CustomerEntity> {
        val users = ArrayList<CustomerEntity>()

        for (i in 1..9) {
            users.add(createCustomer(i))
        }

        return users
    }


    private fun createCustomer(id: Int? = 1): CustomerEntity =
        CustomerEntity(
            id, "Momen$id", "Hamaveisi$id", "${id}23456789", true, single = true
        )
}