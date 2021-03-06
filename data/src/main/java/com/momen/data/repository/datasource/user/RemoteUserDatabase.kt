package com.momen.data.repository.datasource.user

import com.momen.data.entity.UserEntity
import com.momen.data.room.RestelAppDatabase
import io.reactivex.Single
import javax.inject.Inject

class RemoteUserDatabase @Inject constructor(private val room: RestelAppDatabase) : UserDataSource {

    override fun isValidUser(userName: String, password: String, md5: String): Single<UserEntity>? =
        room.getDatabaseDAO()?.isValidUser(userName, password, md5)

    override fun addUser(userEntity: UserEntity): Single<Long>? =
        room.getDatabaseDAO()?.addUser(userEntity)


    override fun editUser(userEntity: UserEntity): Single<Int>? =
        room.getDatabaseDAO()?.editUser(userEntity)

    override fun getUser(id: Int): Single<UserEntity>? =
        room.getDatabaseDAO()?.getUser(id)

    override fun getUsers(): Single<List<UserEntity>>? =
        room.getDatabaseDAO()?.getUsers()

    override fun removeUser(userEntity: UserEntity): Single<Int>? =
        room.getDatabaseDAO()?.removeUser(userEntity)

    override fun recoveryPassword(
        userName: String, nationalCode: String, phoneNumber: String, password: String, md5: String
    ): Single<Int>? =
        room.getDatabaseDAO()?.recoveryPassword(userName, nationalCode, phoneNumber, password, md5)


}
