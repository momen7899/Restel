package com.momen.data.mapper

import com.momen.data.entity.UserEntity
import com.momen.domain.model.User
import javax.inject.Inject

class UserEntityDataMapper  constructor() {
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
            address
        )
    }

    fun transformUserEntityToUser(user: UserEntity): User = with(user) {
        User(
            id!!,
            firstName!!,
            lastName!!,
            nationalCode!!,
            phoneNumber!!,
            userName!!,
            password!!,
            md5!!,
            address!!
        )
    }

}
