package com.momen.data.room

import com.momen.data.entity.UserEntity

class FakeData(private val roomInstance: RoomInstance) {
    val dao = roomInstance.getInstance().getDatabaseDAO()!!
    fun addUser(user: UserEntity) {
        println(dao.fakeAddContact(user))
    }


    fun getUsers() {
        val list = dao.getUsers()
        println(list)
    }
}