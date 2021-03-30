package com.momen.data.repository.user

import com.momen.data.entity.UserEntity
import io.reactivex.Single

interface UserDataSource {
    fun isValidUser(userName: String, password: String, md5: String): Single<UserEntity>
}