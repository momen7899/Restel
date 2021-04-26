package com.momen.restel.room.di

import com.momen.data.mapper.RoomEntityDataMapper
import com.momen.data.repository.datasource.room.RoomDataSourceFactory
import com.momen.data.repository.impl.RoomRepositoryImpl
import com.momen.domain.interactor.AddRoomUseCase
import com.momen.domain.interactor.EditRoomUseCase
import com.momen.domain.interactor.GetRoomUseCase
import com.momen.domain.interactor.RemoveRoomUseCase
import com.momen.domain.repository.RoomRepository
import com.momen.restel.room.model.RoomModelDataMapper
import com.momen.restel.room.viewmodel.RoomViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class RoomModule {
    @Provides
    internal fun provideRoomViewModelFactory(
        addRoomUseCase: AddRoomUseCase,
        editRoomUseCase: EditRoomUseCase,
        getRoomsUseCase: GetRoomUseCase,
        getRoomUseCase: GetRoomUseCase,
        removeRoomUseCase: RemoveRoomUseCase,
        roomModelDataMapper: RoomModelDataMapper
    ) = RoomViewModelFactory(
        addRoomUseCase,
        editRoomUseCase,
        getRoomsUseCase,
        getRoomUseCase,
        removeRoomUseCase,
        roomModelDataMapper
    )


    @Provides
    internal fun provideRoomRepository(
        roomDataSourceFactory: RoomDataSourceFactory,
        roomEntityDataMapper: RoomEntityDataMapper
    ): RoomRepository = RoomRepositoryImpl(
        roomDataSourceFactory,
        roomEntityDataMapper
    )
}