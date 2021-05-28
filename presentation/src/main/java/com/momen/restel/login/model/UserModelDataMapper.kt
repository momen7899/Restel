package com.momen.restel.login.model

import com.momen.domain.model.User
import java.util.*
import javax.inject.Inject

class UserModelDataMapper @Inject constructor() {
    fun transformUsersToUserModels(users: ArrayList<User>?): ArrayList<UserModel> {
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
            transformAdmin(it.admin)
        )
    }

    private fun transformAdmin(admin: Int?): Boolean? {
        if (admin == 1) {
            return true
        } else if (admin == 0)
            return false
        return null
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
            transformBooleanAdmin(admin)
        )
    }

    private fun transformBooleanAdmin(admin: Boolean?): Int? {
        admin?.let {
            if (it) {
                return 1
            } else if (it)
                return 0
        }
        return null
    }


}
