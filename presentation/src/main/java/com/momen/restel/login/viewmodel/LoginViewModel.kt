package com.momen.restel.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.ValidUserUseCase
import com.momen.restel.PasswordGenerator
import com.momen.restel.login.model.UserModel
import com.momen.restel.login.model.UserModelDataMapper
import io.reactivex.disposables.Disposable

class LoginViewModel(
    private val validUserUseCase: ValidUserUseCase,
    private val userModelDataMapper: UserModelDataMapper
) : ViewModel() {

    val loginLiveData = MutableLiveData<Result>()
    private var isValidUser: UserModel? = null
    private var result: Result? = null

    fun isValidUser(userName: String, password: String) {
        result = Result(null, State.LOADING_DATA, null)
        loginLiveData.value = result
        println(userName)
        println(password)
        println(PasswordGenerator.md5(password))

        val params = ValidUserUseCase.Params.forIsValidUser(
            userName,
            password,
            PasswordGenerator.md5(password)
        )
        val d: Disposable = validUserUseCase.execute(params).subscribe({ user ->
            println("id:\t$user")
            isValidUser = userModelDataMapper.transformUserToUserModel(user)
            result = Result(isValidUser, State.DATA_LOADED, null)
            loginLiveData.value = result
        },
            { throwable ->
                isValidUser = null
                println("Login Error View Model $throwable.message")
                result = Result(isValidUser, State.LOAD_ERROR, throwable.message)
                loginLiveData.value = result
            }
        )

    }

    class Result(
        var user: UserModel?,
        var state: State,
        var error: String?,
    )

    enum class State {
        LOADING_DATA,
        DATA_LOADED,
        LOAD_ERROR,
    }
}