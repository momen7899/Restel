package com.momen.data.mapper

import com.momen.data.entity.RoomEntity
import com.momen.domain.model.Room
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RoomEntityDataMapperTest {
    private lateinit var mapper: RoomEntityDataMapper
    private lateinit var roomEntity: RoomEntity
    private lateinit var room: Room

    @Before
    fun setUp() {
        mapper = RoomEntityDataMapper()
        roomEntity = RoomEntity(1, "test", 1, 1)
        room = Room(1, "test", 1, 1)
    }

    @Test
    fun transformRoomEntitiesToRooms() {
        val list = ArrayList<RoomEntity>()
        list.add(roomEntity)
        list.add(roomEntity)
        list.add(roomEntity)

        val actualList = mapper.transformRoomEntitiesToRooms(list)
        val expectList = ArrayList<Room>()
        expectList.add(room)
        expectList.add(room)
        expectList.add(room)
        Assert.assertEquals(expectList, actualList)
    }

    @Test
    fun transformRoomToRoomEntity() {
        val roomEntity = mapper.transformRoomToRoomEntity(room)
        Assert.assertEquals(this.roomEntity, roomEntity)
    }

    @Test
    fun transformRoomEntityToRoom() {
        val room = mapper.transformRoomEntityToRoom(roomEntity)
        Assert.assertEquals(this.room, room)
    }

    @Test
    fun transformRoomEntityToRoomNull() {
        val room = mapper.transformRoomEntityToRoom(null)
        Assert.assertEquals(null, room)
    }
}