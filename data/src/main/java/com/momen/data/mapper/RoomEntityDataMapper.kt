package com.momen.data.mapper

import com.momen.data.entity.RoomEntity
import com.momen.domain.model.Room
import javax.inject.Inject

class RoomEntityDataMapper @Inject constructor() {
    fun transformRoomEntitiesToRooms(roomEntities: List<RoomEntity>?): ArrayList<Room> {
        val rooms = ArrayList<Room>()
        roomEntities?.let {
            for (room in it)
                transformRoomEntityToRoom(room)?.let { item -> rooms.add(item) }
        }
        return rooms
    }




    fun transformRoomToRoomEntity(room:Room): RoomEntity = with(room) {
            RoomEntity(
                    id,
                    roomName,
                    roomCode,
                    customer,
                    startDate,
                    finishDate,
                    price
            )
        }
fun transformRoomEntityToRoom(room: RoomEntity?): Room? =
    room?.let{
        Room(
            it.id,
            it.roomName,
            it.roomCode,
            it.customer,
            it.startDate,
            it.finishDate,
            it.price
        )
    }
}