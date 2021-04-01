package com.momen.data.repository

import com.momen.data.mapper.UserEntityDataMapper
import com.momen.data.repository.user.UserDataSourceFactory
import com.momen.domain.model.User
import com.momen.domain.repository.UserRepository
import io.reactivex.Single

class UserRepositoryImpl(
    private val userDataSourceFactory: UserDataSourceFactory,
    private val userEntityDataMapper: UserEntityDataMapper
) : UserRepository {

    override fun isValidUser(userName: String, password: String, md5: String): Single<User> =
        userDataSourceFactory.create().isValidUser(userName, password, md5)
            .map(userEntityDataMapper::transformUserEntityToUser)

}