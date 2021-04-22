package com.momen.data.mapper

import com.momen.data.entity.UserEntity
import com.momen.domain.model.User
import javax.inject.Inject

class UserEntityDataMapper @Inject constructor() {


    fun transformUserEntitiesToUsers(userEntities: List<UserEntity>): ArrayList<User> {
        val users = arrayListOf<User>()

        if (userEntities.isNotEmpty()) {
            for (userEntity in userEntities) {
                transformUserEntityToUser(userEntity)?.let { users.add(it) }
            }
        }
        return users
    }

    fun transformUserToUserEntity(user: User): UserEntity = with(user) {
        UserEntity(
            id,
            firstName,
            lastName,
            nationalCode,
            phoneNumber,
            userName,
            password,
            md5,
            address,
            admin
        )
    }

    fun transformUserEntityToUser(user: UserEntity?): User? =
        user?.let {
            User(
                it.id,
                it.firstName,
                it.lastName,
                it.nationalCode,
                it.phoneNumber,
                it.userName,
                it.password,
                it.md5,
                it.address,
                it.admin
            )
        }

}
