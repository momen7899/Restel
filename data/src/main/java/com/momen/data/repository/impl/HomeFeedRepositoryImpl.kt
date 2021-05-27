package com.momen.data.repository.impl

import com.momen.data.mapper.CustomerEntityDataMapper
import com.momen.data.mapper.RoomEntityDataMapper
import com.momen.data.repository.datasource.home.HomeDataSourceFactory
import com.momen.domain.model.Customer
import com.momen.domain.model.Room
import com.momen.domain.repository.HomeFeedRepository
import io.reactivex.Single

class HomeFeedRepositoryImpl(
    private val homeDataSourceFactory: HomeDataSourceFactory,
    private val customerEntityDataMapper: CustomerEntityDataMapper,
    private val roomEntityDataMapper: RoomEntityDataMapper
) : HomeFeedRepository {


    override fun getCustomers(): Single<ArrayList<Customer>>? =
        homeDataSourceFactory.create().getCustomers()
            ?.map(customerEntityDataMapper::transformCustomerEntitiesToCustomers)

    override fun getRooms(): Single<ArrayList<Room>>? =
        homeDataSourceFactory.create().getRooms()
            ?.map(roomEntityDataMapper::transformRoomEntitiesToRooms)

}