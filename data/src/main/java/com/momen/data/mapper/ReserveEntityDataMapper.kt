package com.momen.data.mapper

import com.momen.data.entity.ReserveEntity
import com.momen.domain.model.Reserve
import javax.inject.Inject

class ReserveEntityDataMapper @Inject constructor() {

    fun transformReservesEntityToReserves(reserveEntities: List<ReserveEntity>): ArrayList<Reserve> {
        val reserves = ArrayList<Reserve>()
        for (r in reserveEntities) {
            reserves.add(transformReserveEntityToReserve(r))
        }
        return reserves
    }

    private fun transformReserveEntityToReserve(reserve: ReserveEntity): Reserve =
        with(reserve) {
            Reserve(
                id,
                room,
                client,
                customer,
                startDate,
                finishData,
                price
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
