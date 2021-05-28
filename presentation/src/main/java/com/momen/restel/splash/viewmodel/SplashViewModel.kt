package com.momen.restel.splash.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.AddUserUseCase
import com.momen.domain.interactor.GetUsersUseCase
import com.momen.restel.login.model.UserModel
import com.momen.restel.login.model.UserModelDataMapper

class SplashViewModel constructor(
    private val addUserUseCase: AddUserUseCase,
    private val usersUseCase: GetUsersUseCase,
    private val userModelDataMapper: UserModelDataMapper
) : ViewModel() {

    @SuppressLint("CheckResult")
    fun createUser() {
        val usersParam = GetUsersUseCase.Params.forGetUsers()
        usersUseCase.execute(usersParam)?.subscribe({ users ->
            if (users.isEmpty()) addUser()
        }, { throwable ->
            println(throwable.message)
        }
        )
    }

    @SuppressLint("CheckResult")
    private fun addUser() {
        val addUserParam =
            AddUserUseCase.Params.forAddUser(userModelDataMapper.transformUserModelToUser(admin))
        addUserUseCase.execute(addUserParam)?.subscribe({ users ->
            println(users)
        }, { throwable ->
            println(throwable.message)
        }
        )

    }


    private val admin = UserModel(
        1,
        "Momen",
        "Hamaveisi",
        "1234567890",
        "989184394657",
        "admin",
        "admin",
        "21232F297A57A5A743894A0E4A801FC3",
        "admin",
        true
    )

}