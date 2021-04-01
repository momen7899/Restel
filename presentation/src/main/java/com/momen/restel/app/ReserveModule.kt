package com.momen.restel.app

import com.momen.data.mapper.ReserveEntityDataMapper
import com.momen.data.repository.ReserveRepositoryImpl
import com.momen.data.repository.reserve.ReserveDataSourceFactory
import com.momen.domain.interactor.AddReserveUseCase
import com.momen.domain.interactor.GetReservesUseCase
import com.momen.domain.interactor.UpdateReserveUseCase
import com.momen.domain.repository.ReserveRepository
import com.momen.restel.main.viewmodel.MainReserveViewModelFactory
import com.momen.restel.reserve.model.ReserveModelDataMapper
import com.momen.restel.reserve.viewmodel.ReserveViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ReserveModule {
    @Provides
    internal fun provideMainReserveViewModelFactory(
        getReservesUseCase: GetReservesUseCase,
        reserveModelDataMapper: ReserveModelDataMapper
    ) = MainReserveViewModelFactory(
        getReservesUseCase,
        reserveModelDataMapper
    )

    @Provides
    internal fun provideReserveViewModelFactory(
        addUserUseCase: AddReserveUseCase,
        updateUseCase: UpdateReserveUseCase,
        reserveModelDataMapper: ReserveModelDataMapper
    ) = ReserveViewModelFactory(
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