package com.momen.domain.repository

import com.momen.domain.model.User
import io.reactivex.Single

interface UserRepository {
    fun addUser(user: User): Single<Long>

    fun isValidUser(userName: String, password: String, md5: String): Single<Long>

    fun getUser(id: Int): Single<User>
}