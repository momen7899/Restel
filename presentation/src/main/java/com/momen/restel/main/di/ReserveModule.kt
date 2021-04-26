package com.momen.restel.main.di

import com.momen.data.mapper.ReserveEntityDataMapper
import com.momen.data.repository.datasource.reserve.ReserveDataSourceFactory
import com.momen.data.repository.impl.ReserveRepositoryImpl
import com.momen.domain.interactor.AddReserveUseCase
import com.momen.domain.interactor.GetReservesUseCase
import com.momen.domain.interactor.UpdateReserveUseCase
import com.momen.domain.repository.ReserveRepository
import com.momen.restel.main.viewmodel.ReserveViewModelFactory
import com.momen.restel.reserve.model.ReserveModelDataMapper
import dagger.Module
import dagger.Provides

@Module
class ReserveModule {
    @Provides
    internal fun provideReserveViewModelFactory(
        getReservesUseCase: GetReservesUseCase,
        addUserUseCase: AddReserveUseCase,
        updateUseCase: UpdateReserveUseCase,
        reserveModelDataMapper: ReserveModelDataMapper
    ) = ReserveViewModelFactory(
        getReservesUseCase,
        addUserUseCase,
        updateUseCase,
        reserveModelDataMapper
    )


    @Provides
    internal fun provideReserveRepository(
        reserveDataSourceFactory: ReserveDataSourceFactory,
        reserveEntityDataMapper: ReserveEntityDataMapper
    ): ReserveRepository = ReserveRepositoryImpl(
        reserveDataSourceFactory,
        reserveEntityDataMapper
    )
}