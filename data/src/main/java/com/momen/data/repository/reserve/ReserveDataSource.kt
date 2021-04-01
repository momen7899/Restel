package com.momen.data.repository.reserve

import com.momen.data.entity.ReserveEntity
import io.reactivex.Single

interface ReserveDataSource {
    fun addReserve(reserve: ReserveEntity): Single<Long>

    fun updateReserve(reserve: ReserveEntity): Single<Int>

    fun getReserves(): Single<List<ReserveEntity>>
}