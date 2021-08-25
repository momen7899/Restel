package com.momen.data.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.momen.data.entity.RoomEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class RoomTableTest {

    private lateinit var database: RestelAppDatabase
    private lateinit var dao: DatabaseDAO

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RestelAppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getDatabaseDAO()!!
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun addRoom() {
        val room = createRoom()

        dao.addRoom(room)?.test()

        dao.getRooms()?.test()?.assertValue {
            it.contains(room)
        }
    }

    @Test
    fun editRoom() {
        val room = createRoom()

        dao.addRoom(room)?.test()

        val editedRoom = RoomEntity(
            1, "edited", 2, 2
        )

        dao.editRoom(editedRoom)?.test()

        dao.getRooms()?.test()?.assertValue {
            it.contains(editedRoom) && !it.contains(room)
        }
    }

    @Test
    fun getRoom() {
        val room = createRoom()

        dao.addRoom(room)?.test()

        dao.getRoom(1)?.test()?.assertValue {
            it == room
        }
    }

    @Test
    fun getRooms() {
        val rooms = createRooms()

        rooms.forEach {
            dao.addRoom(it)?.test()
        }

        dao.getRooms()?.test()?.assertValue {
            it.containsAll(rooms)
        }
    }

    @Test
    fun removeRoom() {
        val room = createRoom()

        dao.addRoom(room)?.test()

        dao.removeRoom(room)?.test()

        dao.getRooms()?.test()?.assertValue {
            !it.contains(room)
        }
    }


    private fun createRooms(): ArrayList<RoomEntity> {
        val rooms = ArrayList<RoomEntity>()

        for (i in 1..9) {
            rooms.add(createRoom(i))
        }

        return rooms
    }


    private fun createRoom(id: Int? = 1): RoomEntity =
        RoomEntity(
            id, "Momen$id", id, id
        )
}