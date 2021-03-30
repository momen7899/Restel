package com.momen.data.repository.splash

import com.momen.data.entity.UserEntity
import com.momen.domain.model.User
import io.reactivex.Single

interface SplashDataSource {

    fun addUser(user: UserEntity): Single<Long>

    fun getUsers(): Single<List<UserEntity>>
}
