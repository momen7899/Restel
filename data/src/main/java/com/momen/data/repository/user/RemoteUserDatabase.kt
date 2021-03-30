package com.momen.data.repository.user

import com.momen.data.entity.UserEntity
import com.momen.data.room.RestelAppDatabase
import io.reactivex.Single
import javax.inject.Inject

class RemoteUserDatabase @Inject constructor(private val room: RestelAppDatabase) : UserDataSource {

    override fun isValidUser(userName: String, password: String, md5: String): Single<UserEntity> =
        room.getDatabaseDAO()!!.isValidUser(userName, password, md5)

}
