package com.momen.restel.client.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.*
import com.momen.restel.login.model.UserModel
import com.momen.restel.login.model.UserModelDataMapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ClientViewModel(
    private val addUserUseCase: AddUser1UseCase,
    private val editUserUseCase: EditUserUseCase,
    private val usersUseCase: GetUsers1UseCase,
    private val userUseCase: GetUserUseCase,
    private val removeUserUseCase: RemoveUserUseCase,
    private val mapper: UserModelDataMapper
) : ViewModel() {
    val addUserLiveData = MutableLiveData<Result>()
    val editUserLiveData = MutableLiveData<Result>()
    val getUsersLiveData = MutableLiveData<Result>()
    val getUserLiveData = MutableLiveData<Result>()
    val removeUserLiveData = MutableLiveData<Result>()


    private var result: Result? = null
    private val disposables = CompositeDisposable()

    fun addUser(user: UserModel) {
        result = Result(null, State.LOADING_DATA, null)
        addUserLiveData.value = result

        val params = AddUser1UseCase.Params.forAddUser(mapper.transformUserModelToUser(user))
        val d: Disposable? = addUserUseCase.execute(params)?.subscribe({ res ->
            result = Result(null, State.DATA_LOADED, null)
            println(res)
            addUserLiveData.value = result
        }, { throwable ->
            result = Result(null, State.LOAD_ERROR, throwable.message)
            println("Login Error View Model $throwable.message")
            addUserLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    fun editUser(user: UserModel) {
        result = Result(null, State.LOADING_DATA, null)
        editUserLiveData.value = result

        val params = EditUserUseCase.Params.forEditUser(mapper.transformUserModelToUser(user))
        val d: Disposable? = editUserUseCase.execute(params)?.subscribe({ res ->
            result = Result(null, State.DATA_LOADED, null)
            println(res)
            editUserLiveData.value = result
        }, { throwable ->
            result = Result(null, State.LOAD_ERROR, throwable.message)
            println("Login Error View Model $throwable.message")
            editUserLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    fun getUsers() {
        result = Result(null, State.LOADING_DATA, null)
        getUsersLiveData.value = result

        val params = GetUsers1UseCase.Params.forGetUsers()
        val d: Disposable? = usersUseCase.execute(params)?.subscribe({ res ->
            result = Result(mapper.transformUsersToUserModels(res), State.DATA_LOADED, null)
            getUsersLiveData.value = result
        }, { throwable ->
            result = Result(null, State.LOAD_ERROR, throwable.message)
            getUsersLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    fun getUser(id: Int) {
        result = Result(null, State.LOADING_DATA, null)
        getUserLiveData.value = result

        val params = GetUserUseCase.Params.forGetUser(id)
        val d: Disposable? = userUseCase.execute(params)?.subscribe({ res ->
            result = Result(null, State.DATA_LOADED, null)
            println(res)
            getUsersLiveData.value = result
        }, { throwable ->
            result = Result(null, State.LOAD_ERROR, throwable.message)
            println("Login Error View Model $throwable.message")
            getUserLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    fun removeUser(user: UserModel) {
        result = Result(null, State.LOADING_DATA, null)
        removeUserLiveData.value = result

        val params = RemoveUserUseCase.Params.forRemoveUsers(mapper.transformUserModelToUser(user))
        val d: Disposable? = removeUserUseCase.execute(params)?.subscribe({ res ->
            result = Result(null, State.DATA_LOADED, null)
            println(res)
            removeUserLiveData.value = result
        }, { throwable ->
            result = Result(null, State.LOAD_ERROR, throwable.message)
            println("Login Error View Model $throwable.message")
            removeUserLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }
    }

    class Result(
        var data: Any?,
        var state: State,
        var error: String?
    )

    enum class State {
        LOADING_DATA,
        DATA_LOADED,
        LOAD_ERROR
    }

}
