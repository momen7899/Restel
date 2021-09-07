package com.momen.data.mapper

import com.momen.data.entity.CustomerEntity
import com.momen.data.entity.ReserveEntity
import com.momen.domain.model.Customer
import com.momen.domain.model.Reserve
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ReserveEntityDataMapperTest {
    private lateinit var mapper: ReserveEntityDataMapper
    private lateinit var reserveEntity: ReserveEntity
    private lateinit var reserve: Reserve

    @Before
    fun setUp() {
        mapper = ReserveEntityDataMapper()
        reserveEntity =
            ReserveEntity(1, "test", 1, "test", 1, "test", 1, "test", "test", 1)
        reserve = Reserve(1, "test", 1, "test", 1, "test", 1, "test", "test", 1)
    }

    @Test
    fun transformReservesEntityToReserves() {
        val list = ArrayList<ReserveEntity>()
        list.add(reserveEntity)
        list.add(reserveEntity)
        list.add(reserveEntity)

        val actualList = mapper.transformReservesEntityToReserves(list)
        val expectList = ArrayList<Reserve>()
        expectList.add(reserve)
        expectList.add(reserve)
        expectList.add(reserve)
        Assert.assertEquals(expectList, actualList)
    }

    @Test
    fun transformReserveToReserveEntity() {
        val reserveEntity = mapper.transformReserveToReserveEntity(reserve)
        Assert.assertEquals(this.reserveEntity, reserveEntity)
    }

    @Test
    fun transformReserveEntityToReserve() {
        val reserve = mapper.transformReserveEntityToReserve(reserveEntity)
        Assert.assertEquals(this.reserve, reserve)
    }

    @Test
    fun transformReserveEntityToReserveNull() {
        val reserve = mapper.transformReserveEntityToReserve(null)
        Assert.assertEquals(null, reserve)
    }
}