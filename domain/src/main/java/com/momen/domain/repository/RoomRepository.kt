package com.momen.domain.repository

import com.momen.domain.model.Room
import io.reactivex.Single

interface RoomRepository {

    fun addRoom(room: Room): Single<Long>?

    fun editRoom(room: Room): Single<Int>?

    fun getRooms(): Single<ArrayList<Room>>?

    fun getRoom(id: Int): Single<Room>?

    fun removeRoom(room: Room): Single<Int>?
}