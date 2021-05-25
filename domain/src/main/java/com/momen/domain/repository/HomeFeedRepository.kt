package com.momen.domain.repository

import com.momen.domain.model.Customer
import com.momen.domain.model.Room
import io.reactivex.Single


interface HomeFeedRepository {

    fun getCustomers(): Single<ArrayList<Customer>>?

    fun getRooms(): Single<ArrayList<Room>>?
}