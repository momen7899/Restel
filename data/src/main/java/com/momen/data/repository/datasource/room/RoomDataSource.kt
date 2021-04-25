package com.momen.data.repository.datasource.room

import com.momen.data.entity.RoomEntity
import io.reactivex.Single

interface RoomDataSource {

    fun addRoom(roomEntity: RoomEntity): Single<Long>?

    fun editRoom(roomEntity: RoomEntity): Single<Int>?

    fun getRooms(): Single<List<RoomEntity>>?

    fun getRoom(id: Int): Single<RoomEntity>?

    fun removeRooms(roomEntity: RoomEntity): Single<Int>?

}