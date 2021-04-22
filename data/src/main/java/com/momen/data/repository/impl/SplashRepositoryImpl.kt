package com.momen.data.repository.impl

import com.momen.data.mapper.UserEntityDataMapper
import com.momen.data.repository.datasource.splash.SplashDataSourceFactory
import com.momen.domain.model.User
import com.momen.domain.repository.SplashRepository
import io.reactivex.Single

class SplashRepositoryImpl(
    private val splashDataSourceFactory: SplashDataSourceFactory,
    private val userEntityDataMapper: UserEntityDataMapper
) : SplashRepository {

    override fun getUsers(): Single<ArrayList<User>> =
        splashDataSourceFactory.create().getUsers()
            .map(userEntityDataMapper::transformUserEntitiesToUsers)

    override fun addUser(user: User): Single<Long> =
        splashDataSourceFactory.create().addUser(userEntityDataMapper.transformUserToUserEntity(user))


}