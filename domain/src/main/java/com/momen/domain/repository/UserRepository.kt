package com.momen.domain.repository

import com.momen.domain.model.User
import io.reactivex.Single

interface UserRepository {
    fun isValidUser(userName: String, password: String, md5: String): Single<User>
}