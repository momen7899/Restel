package com.momen.data.repository.datasource.reserve

import com.momen.data.entity.ReserveEntity
import com.momen.data.room.RestelAppDatabase
import io.reactivex.Single
import javax.inject.Inject

class RemoteReserveDataSource @Inject constructor(private val room: RestelAppDatabase) :
    ReserveDataSource {

    override fun addReserve(reserve: ReserveEntity): Single<Long>? =
        room.getDatabaseDAO()?.addReserve(reserve)


    override fun updateReserve(reserve: ReserveEntity): Single<Int>? =
        room.getDatabaseDAO()?.updateReserve(reserve)

    override fun getReserves(): Single<List<ReserveEntity>>? =
        room.getDatabaseDAO()?.getReserves()

    override fun removeReserve(reserveEntity: ReserveEntity): Single<Int>? =
        room.getDatabaseDAO()?.removeReserve(reserveEntity)

}