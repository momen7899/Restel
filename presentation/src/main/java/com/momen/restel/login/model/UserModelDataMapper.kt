package com.momen.restel.login.model

import com.momen.domain.model.User
import javax.inject.Inject

class UserModelDataMapper @Inject constructor() {
    fun transformUserToUserModel(user: User): UserModel = with(user) {
        UserModel(
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

    fun transformUserModelToUser(user: UserModel): User = with(user) {
        User(
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

}
