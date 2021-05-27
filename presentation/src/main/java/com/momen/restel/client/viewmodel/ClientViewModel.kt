package com.momen.restel.client.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.AddUser1UseCase
import com.momen.domain.interactor.EditUserUseCase
import com.momen.domain.interactor.GetUsers1UseCase
import com.momen.domain.interactor.RemoveUserUseCase
import com.momen.restel.login.model.UserModel
import com.momen.restel.login.model.UserModelDataMapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ClientViewModel(
    private val addUserUseCase: AddUser1UseCase,
    private val editUserUseCase: EditUserUseCase,
    private val usersUseCase: GetUsers1UseCase,
    private val removeUserUseCase: RemoveUserUseCase,
    private val mapper: UserModelDataMapper
) : ViewModel() {
    val addUserLiveData = MutableLiveData<Result>()
    val editUserLiveData = MutableLiveData<Result>()
    val getUsersLiveData = MutableLiveData<Result>()
    val getUserLiveData = MutableLiveData<Result>()
    val removeUserLiveData = MutableLiveData<Result>()


    private val disposables = CompositeDisposable()

    fun addUser(user: UserModel) {
        var result: Result?
        result = Result(null, null, State.LOADING_DATA, null)
        addUserLiveData.value = result
        println(user.id)
        val params = AddUser1UseCase.Params.forAddUser(mapper.transformUserModelToUser(user))
        val d: Disposable? = addUserUseCase.execute(params)?.subscribe({ res ->
            result = Result(res, null, State.DATA_LOADED, null)
            addUserLiveData.value = result
        }, { throwable ->
            result = Result(null, null, State.LOAD_ERROR, throwable.message)
            println("Login Error View Model $throwable.message")
            addUserLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    fun editUser(user: UserModel) {
        var result: Result?
        result = Result(null, null, State.LOADING_DATA, null)
        editUserLiveData.value = result
        println(user)
        val params = EditUserUseCase.Params.forEditUser(mapper.transformUserModelToUser(user))
        val d: Disposable? = editUserUseCase.execute(params)?.subscribe({ res ->
            result = Result(res.toLong(), null, State.DATA_LOADED, null)
            editUserLiveData.value = result
        }, { throwable ->
            result = Result(null, null, State.LOAD_ERROR, throwable.message)
            editUserLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    fun getUsers() {
        var result: Result?
        result = Result(null, null, State.LOADING_DATA, null)
        getUsersLiveData.value = result

        val params = GetUsers1UseCase.Params.forGetUsers()
        val d: Disposable? = usersUseCase.execute(params)?.subscribe({ res ->
            result = Result(null, mapper.transformUsersToUserModels(res), State.DATA_LOADED, null)
            getUsersLiveData.value = result
        }, { throwable ->
            result = Result(null, null, State.LOAD_ERROR, throwable.message)
            getUsersLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    fun removeUser(user: UserModel) {
        var result: Result?
        result = Result(null, null, State.LOADING_DATA, null)
        removeUserLiveData.postValue(result)

        val params = RemoveUserUseCase.Params.forRemoveUsers(mapper.transformUserModelToUser(user))
        val d: Disposable? = removeUserUseCase.execute(params)?.subscribe({ res ->
            result = Result(res.toLong(), null, State.DATA_LOADED, null)
            println(res)
            removeUserLiveData.value = result
        }, { throwable ->
            result = Result(null, null, State.LOAD_ERROR, throwable.message)
            println("Login Error View Model $throwable.message")
            removeUserLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }
    }

    class Result(
        var response: Long?,
        var users: ArrayList<UserModel>?,
        var state: State,
        var error: String?
    )

    enum class State {
        LOADING_DATA,
        DATA_LOADED,
        LOAD_ERROR
    }

}
