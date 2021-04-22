package com.momen.data.repository.impl

import com.momen.data.mapper.UserEntityDataMapper
import com.momen.data.repository.datasource.user.UserDataSourceFactory
import com.momen.domain.model.User
import com.momen.domain.repository.UserRepository
import io.reactivex.Single

class UserRepositoryImpl(
    private val userDataSourceFactory: UserDataSourceFactory,
    private val userEntityDataMapper: UserEntityDataMapper
) : UserRepository {

    override fun isValidUser(userName: String, password: String, md5: String): Single<User> =
        userDataSourceFactory.create().isValidUser(userName, password, md5)
            ?.map(userEntityDataMapper::transformUserEntityToUser)!!

    override fun addUser(user: User): Single<User>? {
        TODO("Not yet implemented")
    }

    override fun editUser(user: User): Single<User>? {
        TODO("Not yet implemented")
    }

    override fun getUsers(): Single<ArrayList<User>>? {
        TODO("Not yet implemented")
    }

    override fun getUser(userId: Int): Single<User>? {
        TODO("Not yet implemented")
    }

    override fun removeUser(userId: Int): Single<User>? {
        TODO("Not yet implemented")
    }

}