package com.momen.domain.repository

import com.momen.domain.model.Reserve
import io.reactivex.Single

interface ReserveRepository {
    fun addReserve(reserve: Reserve): Single<Long>?

    fun updateReserve(reserve: Reserve): Single<Int>?

    fun getReserves(): Single<ArrayList<Reserve>>?
}