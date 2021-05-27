package com.momen.restel.login.model

import com.momen.domain.model.User
import java.util.*
import javax.inject.Inject

class UserModelDataMapper @Inject constructor() {
    fun transformUsersToUserModels(users: ArrayList<User>?): ArrayList<UserModel>? {
        val list = ArrayList<UserModel>()
        users?.forEach {
            transformUserToUserModel(it)?.let { it1 -> list.add(it1) }
        }
        return list
    }

    fun transformUserToUserModel(user: User?): UserModel? = user?.let {
        UserModel(
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
            address,
            admin
        )
    }


}
