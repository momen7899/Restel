package com.momen.restel.room.model

import com.momen.domain.model.Room
import javax.inject.Inject

class RoomModelDataMapper @Inject constructor() {

    fun transformRoomsToRoomModels(data: ArrayList<Room>?): ArrayList<RoomModel> {
        val list = ArrayList<RoomModel>()
        data?.let {
            data.forEach {
                list.add(transformRoomToRoomModel(it))
            }
        }
        return list
    }

    private fun transformRoomToRoomModel(room: Room): RoomModel =
        with(room) { RoomModel(0, roomName.toString(), capacity.toString(), price.toString()) }


    fun transformRoomModelToRoom(room: RoomModel): Room =
        with(room) { Room(0, name, capacity.toInt(), price.toInt()) }


}
