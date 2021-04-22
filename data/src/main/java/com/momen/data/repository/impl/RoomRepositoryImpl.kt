package com.momen.data.repository.impl

import com.momen.data.mapper.RoomEntityDataMapper
import com.momen.data.repository.datasource.room.RoomDataSourceFactory
import com.momen.domain.model.Room
import com.momen.domain.repository.RoomRepository
import io.reactivex.Single

class RoomRepositoryImpl(
    private val roomDataSource: RoomDataSourceFactory,
    private val roomEntityDataMapper: RoomEntityDataMapper
) : RoomRepository {

    override fun addRoom(room: Room): Single<Long>? =
        roomDataSource.create().addRoom(roomEntityDataMapper.transformRoomToRoomEntity(room))

    override fun editRoom(room: Room): Single<Room>? =
        roomDataSource.create().editRoom(roomEntityDataMapper.transformRoomToRoomEntity(room))
            ?.map(roomEntityDataMapper::transformRoomEntityToRoom)

    override fun getRooms(): Single<ArrayList<Room>>? =
        roomDataSource.create().getRooms()
            ?.map(roomEntityDataMapper::transformRoomEntitiesToRooms)

    override fun getRoom(id: Int): Single<Room>? =
        roomDataSource.create().getRoom(id)
            ?.map(roomEntityDataMapper::transformRoomEntityToRoom)

    override fun removeRoom(id: Int): Single<Int>? =
        roomDataSource.create().removeRooms(id)
}