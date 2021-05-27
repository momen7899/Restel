package com.momen.data.repository.datasource.home

import com.momen.data.entity.CustomerEntity
import com.momen.data.entity.RoomEntity
import io.reactivex.Single

interface HomeDataSource {

    fun getCustomers(): Single<List<CustomerEntity>>?

    fun getRooms(): Single<List<RoomEntity>>?

}