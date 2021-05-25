package com.momen.data.repository.datasource.home

import com.momen.data.entity.CustomerEntity
import com.momen.data.entity.RoomEntity
import com.momen.data.room.RestelAppDatabase
import io.reactivex.Single
import javax.inject.Inject

class RemoteHomeDatabase @Inject constructor(private val room: RestelAppDatabase) :
    HomeDataSource {

    override fun getCustomers(): Single<List<CustomerEntity>>? =
        room.getDatabaseDAO()?.getCustomers()


    override fun getRooms(): Single<List<RoomEntity>>? =
        room.getDatabaseDAO()?.getRooms()

}