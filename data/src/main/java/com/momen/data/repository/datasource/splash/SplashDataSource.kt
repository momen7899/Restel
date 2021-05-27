package com.momen.data.repository.datasource.splash

import com.momen.data.entity.UserEntity
import io.reactivex.Single

interface SplashDataSource {

    fun addUser(user: UserEntity): Single<Long>

    fun getUsers(): Single<List<UserEntity>>
}
