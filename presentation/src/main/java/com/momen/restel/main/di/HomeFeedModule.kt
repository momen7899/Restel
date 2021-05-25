package com.momen.restel.main.di

import com.momen.data.mapper.CustomerEntityDataMapper
import com.momen.data.mapper.RoomEntityDataMapper
import com.momen.data.repository.datasource.home.HomeDataSourceFactory
import com.momen.data.repository.impl.HomeFeedRepositoryImpl
import com.momen.domain.interactor.HomeGetCustomersUseCase
import com.momen.domain.interactor.HomeGetRoomsUseCase
import com.momen.domain.repository.HomeFeedRepository
import com.momen.restel.main.model.HomeCustomerDataMapper
import com.momen.restel.main.model.HomeRoomDataMapper
import com.momen.restel.main.viewmodel.HomeFeedViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class HomeFeedModule {

    @Provides
    internal fun provideHomeFeedViewModelFactory(
        customersUseCase: HomeGetCustomersUseCase,
        roomsUseCase: HomeGetRoomsUseCase,
        customerDataMapper: HomeCustomerDataMapper,
        roomDataMapper: HomeRoomDataMapper
    ) = HomeFeedViewModelFactory(
        customersUseCase,
        roomsUseCase,
        customerDataMapper,
        roomDataMapper
    )

    @Provides
    internal fun provideHomeFeedRepository(
        homeDataSourceFactory: HomeDataSourceFactory,
        customerEntityDataMapper: CustomerEntityDataMapper,
        roomEntityDataMapper: RoomEntityDataMapper
    ): HomeFeedRepository = HomeFeedRepositoryImpl(
        homeDataSourceFactory,
        customerEntityDataMapper,
        roomEntityDataMapper
    )
}