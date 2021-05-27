package com.momen.data.repository.impl

import com.momen.data.mapper.ReserveEntityDataMapper
import com.momen.data.repository.datasource.reserve.ReserveDataSourceFactory
import com.momen.domain.model.Reserve
import com.momen.domain.repository.ReserveRepository
import io.reactivex.Single

class ReserveRepositoryImpl(
    private val reserveDataSourceFactory: ReserveDataSourceFactory,
    private val reserveEntityDataMapper: ReserveEntityDataMapper
) : ReserveRepository {

    override fun addReserve(reserve: Reserve): Single<Long>? =
        reserveDataSourceFactory.create()
            .addReserve(reserveEntityDataMapper.transformReserveToReserveEntity(reserve))

    override fun updateReserve(reserve: Reserve): Single<Int>? =
        reserveDataSourceFactory.create()
        .updateReserve(reserveEntityDataMapper.transformReserveToReserveEntity(reserve))


    override fun getReserves(): Single<ArrayList<Reserve>>? =
        reserveDataSourceFactory.create().getReserves()?.map(reserveEntityDataMapper::transformReservesEntityToReserves)

}