package com.momen.data.mapper

import com.momen.data.entity.ReserveEntity
import com.momen.domain.model.Reserve
import javax.inject.Inject

class ReserveEntityDataMapper @Inject constructor() {

    fun transformReservesEntityToReserves(reserveEntities: List<ReserveEntity>): ArrayList<Reserve> {
        val reserves = ArrayList<Reserve>()
        for (r in reserveEntities) {
            transformReserveEntityToReserve(r)?.let { reserves.add(it) }
        }
        return reserves
    }

    private fun transformReserveEntityToReserve(reserve: ReserveEntity?): Reserve? =
        reserve?.let {
            Reserve(
                it.id,
                it.room,
                it.client,
                it.customer,
                it.startDate,
                it.finishData,
                it.price
            )
        }

    fun transformReserveToReserveEntity(reserve: Reserve): ReserveEntity =
        with(reserve) {
            ReserveEntity(
                id,
                room,
                client,
                customer,
                startDate,
                finishData,
                price
            )
        }


}
