package com.momen.domain.repository

import com.momen.domain.model.User
import io.reactivex.Single

interface SplashRepository {
    fun getUsers(): Single<ArrayList<User>>

    fun addUser(user: User): Single<Long>
}