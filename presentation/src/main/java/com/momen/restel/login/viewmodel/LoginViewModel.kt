package com.momen.restel.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.RecoveryPasswordUseCase
import com.momen.domain.interactor.ValidUserUseCase
import com.momen.restel.Utils
import com.momen.restel.login.model.UserModel
import com.momen.restel.login.model.UserModelDataMapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class LoginViewModel(
    private val validUserUseCase: ValidUserUseCase,
    private val recoveryPassword: RecoveryPasswordUseCase,
    private val userModelDataMapper: UserModelDataMapper
) : ViewModel() {

    val loginLiveData = MutableLiveData<Result>()
    val recoveryLiveData = MutableLiveData<Result>()
    private var isValidUser: UserModel? = null
    private var result: Result? = null
    private val disposables = CompositeDisposable()

    fun isValidUser(userName: String, password: String) {
        result = Result(null, State.LOADING_DATA, null)
        loginLiveData.value = result

        val params = ValidUserUseCase.Params.forIsValidUser(
            userName,
            password,
            Utils.md5(password)
        )
        val d: Disposable? = validUserUseCase.execute(params)?.subscribe({ user ->
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
        d?.let { disposables.add(it) }
    }

    fun recoveryPassword(userName: String, userNational: String, phoneNumber: String) {
        result = Result(null, State.LOADING_DATA, null)
        recoveryLiveData.value = result

        val params = RecoveryPasswordUseCase.Params.forRecoveryPassword(
            userName, userNational, phoneNumber, "1234", Utils.md5("1234")
        )

        val d: Disposable? = recoveryPassword.execute(params)?.subscribe({ user ->
            result = Result(null, State.DATA_LOADED, null)
            recoveryLiveData.value = result
        },
            { throwable ->
                println("Login Error View Model $throwable.message")
                result = Result(null, State.LOAD_ERROR, throwable.message)
                recoveryLiveData.value = result
            }
        )
        d?.let { disposables.add(it) }
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