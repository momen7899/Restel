package com.momen.restel.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.ValidUserUseCase
import com.momen.restel.PasswordGenerator
import io.reactivex.disposables.Disposable

class LoginViewModel(
    private val validUserUseCase: ValidUserUseCase,
) : ViewModel() {

    val loginLiveData = MutableLiveData<Result>()
    private var isValidUser: Boolean? = null
    private var result: Result? = null

    fun isValidUser(userName: String, password: String) {
        result = Result(null, State.LOADING_DATA, null)
        loginLiveData.value = result
        val params = ValidUserUseCase.Params.forIsValidUser(
            userName,
            password,
            PasswordGenerator.md5(password)
        )

        val d: Disposable = validUserUseCase.execute(params).subscribe({ id ->
            println("id:\t$id")
            isValidUser = false
            if (id > 0) isValidUser = true
            result = Result(isValidUser, State.DATA_LOADED, null)
            loginLiveData.value = result
        },
            { throwable ->
                isValidUser = null
                println(throwable.message)
                result = Result(isValidUser, State.LOAD_ERROR, throwable.message)
                loginLiveData.value = result
            }
        )

    }

    class Result(
        var boolean: Boolean?,
        var state: State,
        var error: String?,
    )

    enum class State {
        LOADING_DATA,
        DATA_LOADED,
        LOAD_ERROR,
    }
}