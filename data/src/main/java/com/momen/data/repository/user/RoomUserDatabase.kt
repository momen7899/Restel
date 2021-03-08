package com.momen.data.repository.user

import com.momen.data.entity.UserEntity
import com.momen.data.room.RoomInstance
import io.reactivex.Single
import javax.inject.Inject

class RoomUserDatabase  constructor(
    roomInstance: RoomInstance
) : UserDataSource {

    private val dao = roomInstance.getInstance().getDatabaseDAO()

    override fun addUser(user: UserEntity): Single<Long> = dao?.addContact(user)!!

    override fun isValidUser(userName: String, password: String, md5: String): Single<Long> =
        dao?.isValidUser(userName, password, md5)!!

    override fun getUser(id: Int): Single<UserEntity> = dao?.getUser(id)!!
}
