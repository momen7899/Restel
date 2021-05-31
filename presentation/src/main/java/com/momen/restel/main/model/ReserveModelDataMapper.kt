package com.momen.restel.main.model

import com.momen.domain.model.Reserve
import javax.inject.Inject

class ReserveModelDataMapper @Inject constructor() {
    fun transformReserveToReserveModels(reserves: ArrayList<Reserve>): ArrayList<ReserveModel> {
        val list = ArrayList<ReserveModel>()
        for (reserve in reserves) {
            list.add(transformReserveToReserveModel(reserve))
        }
        println("reservesModel $list")
        return list
    }

    private fun transformReserveToReserveModel(reserve: Reserve): ReserveModel =
        with(reserve) {
            ReserveModel(
                id,
                room,
                roomId,
                client,
                clientId,
                customer,
                customerId,
                startDate,
                finishData,
                price
            )
        }

    fun transformReserveModelToReserve(reserve: ReserveModel): Reserve =
        with(reserve) {
            Reserve(
                id,
                room,
                roomId,
                client,
                clientId,
                customer,
                customerId,
                startDate,
                finishData,
                price
            )
        }

}
