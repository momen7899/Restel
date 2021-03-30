package com.momen.data.repository.splash

import com.momen.data.entity.UserEntity
import com.momen.data.room.RestelAppDatabase
import io.reactivex.Single
import javax.inject.Inject

class RemoteSplashDataSource @Inject constructor(private val room: RestelAppDatabase) :
    SplashDataSource {

    override fun addUser(user: UserEntity): Single<Long> = room.getDatabaseDAO()!!.addContact(user)

    override fun getUsers(): Single<List<UserEntity>> {
        return room.getDatabaseDAO()?.getUsers()!!
    }

}