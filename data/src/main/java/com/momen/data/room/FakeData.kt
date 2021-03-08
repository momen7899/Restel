package com.momen.data.room

import com.momen.data.entity.UserEntity

class FakeData(roomInstance: RoomInstance) {
    private val dao = roomInstance.getInstance().getDatabaseDAO()!!
    fun addUser() {
        val user = UserEntity(
            null,
            "Momen",
            "Hamaveisi",
            "1234567890",
            "989184394657",
            "admin",
            "admin",
            "21232F297A57A5A743894A0E4A801FC3",
            "admin"
        )

        if (getUsers() == 0) {
            println("Add Contact")
            println(dao.fakeAddContact(user))
        }

    }


    private fun getUsers(): Int {
        val list = dao.getUsers()
        println(list + "\t" + list.size)
        return list.size
    }
}