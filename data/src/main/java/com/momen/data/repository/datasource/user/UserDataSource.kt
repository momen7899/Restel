package com.momen.data.repository.datasource.user

import com.momen.data.entity.UserEntity
import io.reactivex.Single

interface UserDataSource {
    fun isValidUser(userName: String, password: String, md5: String): Single<UserEntity>?

    fun addUser(userEntity: UserEntity): Single<Long>?

    fun editUser(userEntity: UserEntity): Single<Int>?

    fun getUser(id: Int): Single<UserEntity>?

    fun getUsers(): Single<List<UserEntity>>?

    fun removeUser(userEntity: UserEntity): Single<Int>?

    fun recoveryPassword(
        userName: String,
        nationalCode: String,
        phoneNumber: String,
        password: String,
        md5: String
    ): Single<Int>?
}