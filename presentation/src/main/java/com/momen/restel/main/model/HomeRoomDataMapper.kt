package com.momen.restel.main.model

import com.momen.domain.model.Room
import javax.inject.Inject

class HomeRoomDataMapper @Inject constructor() {

    fun transformRoomsToRoomModels(data: ArrayList<Room>?): ArrayList<HomeRoomModel> {
        val list = ArrayList<HomeRoomModel>()
        data?.let {
            data.forEach {
                list.add(transformRoomToRoomModel(it))
            }
        }
        return list
    }

    private fun transformRoomToRoomModel(room: Room): HomeRoomModel = with(room)
    { HomeRoomModel(id, roomName.toString(), capacity.toString(), price.toString()) }

}

