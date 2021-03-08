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

    override fun addUser(user: User): Single<Long> =
        userDataSourceFactory.create().addUser(userEntityDataMapper.transformUserToUserEntity(user))

    override fun isValidUser(userName: String, password: String, md5: String): Single<Long> =
        userDataSourceFactory.create().isValidUser(userName, password, md5)

    override fun getUser(id: Int): Single<User> =
        userDataSourceFactory.create().getUser(id)
            .map(userEntityDataMapper::transformUserEntityToUser)

}