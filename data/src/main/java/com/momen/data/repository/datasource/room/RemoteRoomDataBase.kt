package com.momen.data.repository.datasource.room

import android.util.Log
import com.momen.data.entity.RoomEntity
import com.momen.data.room.RestelAppDatabase
import io.reactivex.Single
import javax.inject.Inject

class RemoteRoomDataBase @Inject constructor(private val room: RestelAppDatabase) : RoomDataSource {
    override fun addRoom(roomEntity: RoomEntity): Single<Long>? {
        Log.i("AddRoom", roomEntity.toString())
        return room.getDatabaseDAO()?.addRoom(roomEntity)
    }

    override fun editRoom(roomEntity: RoomEntity): Single<Int>? =
        room.getDatabaseDAO()?.editRoom(roomEntity)

    override fun getRooms(): Single<List<RoomEntity>>? =
        room.getDatabaseDAO()?.getRooms()

    override fun getRoom(id: Int): Single<RoomEntity>? =
        room.getDatabaseDAO()?.getRoom(id)

    override fun removeRooms(roomEntity: RoomEntity): Single<Int>? =
        room.getDatabaseDAO()?.removeRoom(roomEntity)

}