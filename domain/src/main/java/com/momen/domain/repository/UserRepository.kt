package com.momen.domain.repository

import com.momen.domain.model.User
import io.reactivex.Single

interface UserRepository {
    fun isValidUser(userName: String, password: String, md5: String): Single<User>?

    fun recoveryPassword(
        userName: String,
        nationalCode: String,
        phoneNumber: String,
        password: String,
        md5: String
    ): Single<Int>?

    fun addUser(user: User): Single<Long>?

    fun editUser(user: User): Single<Int>?

    fun getUsers(): Single<ArrayList<User>>?

    fun getUser(userId: Int): Single<User>?

    fun removeUser(room: User): Single<Int>?
}