package com.momen.data.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.momen.data.entity.ReserveEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ReserveTableTest {
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
    fun addReserve() {
        val reserve = createReserve()

        dao.addReserve(reserve)?.test()

        dao.getReserves()?.test()?.assertValue {
            it.contains(reserve)
        }
    }

    @Test
    fun editReserve() {
        val reserve = createReserve()

        dao.addReserve(reserve)?.test()

        val editedReserve = ReserveEntity(
            1, "edited", 1, "edited", 1, "edited", 1, "edited", "edited", 1
        )

        dao.updateReserve(editedReserve)?.test()

        dao.getReserves()?.test()?.assertValue {
            it.contains(editedReserve) && !it.contains(reserve)
        }
    }

    @Test
    fun getReserves() {
        val reserves = createReserves()

        reserves.forEach {
            dao.addReserve(it)?.test()
        }

        dao.getReserves()?.test()?.assertValue {
            it.containsAll(reserves)
        }
    }

    @Test
    fun removeReserves() {
        val reserve = createReserve()

        dao.addReserve(reserve)?.test()

        dao.removeReserve(reserve)?.test()

        dao.getReserves()?.test()?.assertValue {
            !it.contains(reserve)
        }
    }

    private fun createReserves(): ArrayList<ReserveEntity> {
        val reserves = ArrayList<ReserveEntity>()

        for (i in 1..9) {
            reserves.add(createReserve(i))
        }

        return reserves
    }


    private fun createReserve(id: Int? = 1): ReserveEntity =
        ReserveEntity(
            id, "room$id", id, "room$id", id, "customer$id", id, "start", "finish", id
        )
}