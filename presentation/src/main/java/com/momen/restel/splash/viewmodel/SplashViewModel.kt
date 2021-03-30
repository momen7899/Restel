package com.momen.restel.splash.viewmodel

import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.AddUserUseCase
import com.momen.domain.interactor.GetUsersUseCase
import com.momen.restel.login.model.UserModel
import com.momen.restel.login.model.UserModelDataMapper
import io.reactivex.disposables.Disposable

class SplashViewModel constructor(
    private val addUserUseCase: AddUserUseCase,
    private val usersUseCase: GetUsersUseCase,
    private val userModelDataMapper: UserModelDataMapper
) : ViewModel() {

    fun createUser() {
        println("Create User")
        val usersParam = GetUsersUseCase.Params.forGetUsers()
        val d: Disposable =
            usersUseCase.execute(usersParam).subscribe({ users ->
                println(users)
                if (users.isEmpty()) addUser()

            }, { throwable ->
                println(throwable.message)
            }
            )
    }

    private fun addUser() {
        val addUserParam =
            AddUserUseCase.Params.forAddUser(userModelDataMapper.transformUserModelToUser(admin))
        val d: Disposable =
            addUserUseCase.execute(addUserParam).subscribe({ users ->
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
        1
    )

}
