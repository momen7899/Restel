package com.momen.data.repository.user

import com.momen.data.entity.UserEntity
import io.reactivex.Single

interface UserDataSource {
    fun addUser(user: UserEntity): Single<Long>

    fun isValidUser(userName: String, password: String, md5: String): Single<Long>

    fun getUser(id: Int): Single<UserEntity>

}