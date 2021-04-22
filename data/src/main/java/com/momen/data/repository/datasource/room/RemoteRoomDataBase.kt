package com.momen.data.repository.datasource.room

import com.momen.data.entity.RoomEntity
import com.momen.data.room.RestelAppDatabase
import io.reactivex.Single
import javax.inject.Inject

class RemoteRoomDataBase @Inject constructor(private val room: RestelAppDatabase) : RoomDataSource {
    override fun addRoom(roomEntity: RoomEntity): Single<Long>? =
        room.getDatabaseDAO()?.addRoom(roomEntity)


    override fun editRoom(roomEntity: RoomEntity): Single<RoomEntity>? =
        room.getDatabaseDAO()?.editRoom(roomEntity)

    override fun getRooms(): Single<List<RoomEntity>>? =
        room.getDatabaseDAO()?.getRooms()

    override fun getRoom(id: Int): Single<RoomEntity>? =
        room.getDatabaseDAO()?.getRoom(id)

    override fun removeRooms(id: Int): Single<Int>? =
        room.getDatabaseDAO()?.removeRoom(id)

}