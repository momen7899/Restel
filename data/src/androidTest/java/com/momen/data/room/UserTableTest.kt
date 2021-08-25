package com.momen.data.room

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.momen.data.entity.UserEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class UserTableTest {

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
    fun insertUser() {
        val user = createUser()

        dao.addUser(user)?.test()

        dao.getUsers()?.test()?.assertValue {
            it.contains(user)
        }
    }

    @Test
    fun deleteUser() {
        val user = createUser()

        dao.addUser(user)?.test()

        dao.removeUser(user)?.test()

        dao.getUsers()?.test()?.assertValue { users ->
            !users.contains(user)
        }
    }

    @Test
    fun updateUser() {
        val user = createUser()

        dao.addUser(user)?.test()

        val editedUser = UserEntity(
            1, "edited", "edited", "edited", "edited",
            "edited", "edited", "edited", "edited", 0
        )

        dao.editUser(editedUser)?.test()

        dao.getUsers()?.test()?.assertValue {
            it.contains(editedUser) && !it.contains(user)
        }
    }

    @Test
    fun getUser() {
        val user = createUser()

        dao.addUser(user)?.test()

        dao.getUser(1)?.test()?.assertValue {
            user == it
        }

    }

    @Test
    fun isValidUser() {
        val user = createUser()

        dao.addUser(user)?.test()

        dao.isValidUser(user.userName!!, user.password!!, user.md5!!)?.test()?.assertValue {
            it == user
        }
    }

    @Test
    fun recoveryPassword() {
        val user = createUser()

        dao.addUser(user)?.test()


        dao.recoveryPassword(
            user.userName!!, user.nationalCode!!, user.phoneNumber!!,
            user.password!!, user.md5!!
        )?.test()?.assertValue {
            it == user.id
        }
    }

    @Test
    fun getUsers() {
        val users = createUsers()

        users.forEach {
            dao.addUser(it)?.test()
        }

        dao.getUsers()?.test()?.assertValue {
            it.containsAll(users)
        }
    }

    private fun createUsers(): ArrayList<UserEntity> {
        val users = ArrayList<UserEntity>()

        for (i in 1..9) {
            users.add(createUser(i))
        }

        return users
    }


    private fun createUser(id: Int? = 1): UserEntity =
        UserEntity(
            id, "Momen$id", "Hamaveisi$id", "${id}23456789", "9{$id}81234567",
            "momen7899$id", "pass$id", "pass$id", "address$id", 1
        )

}
