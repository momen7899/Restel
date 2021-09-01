package com.momen.restel.splash

import com.momen.domain.model.User
import com.momen.domain.repository.SplashRepository
import io.reactivex.Single

class FakeSplashRepository : SplashRepository {

    override fun getUsers(): Single<ArrayList<User>>? =
        Single.create {
            createUsers()
        }


    override fun addUser(user: User): Single<Long>? {
        return null
    }


    private fun createUsers(): ArrayList<User> {
        val users = ArrayList<User>()

        for (i in 1..9) {
            users.add(createUser(i))
        }

        return users
    }


    private fun createUser(id: Int? = 1): User =
        User(
            id, "Momen$id", "Hamaveisi$id", "${id}23456789", "9{$id}81234567",
            "momen7899$id", "pass$id", "pass$id", "address$id", 1
        )

}