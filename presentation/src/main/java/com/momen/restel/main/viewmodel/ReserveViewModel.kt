package com.momen.restel.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.AddReserveUseCase
import com.momen.domain.interactor.GetReservesUseCase
import com.momen.domain.interactor.RemoveReserveUseCase
import com.momen.domain.interactor.UpdateReserveUseCase
import com.momen.restel.main.model.ReserveModel
import com.momen.restel.main.model.ReserveModelDataMapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ReserveViewModel(
    private val reservesUseCase: GetReservesUseCase,
    private val addReserveUseCase: AddReserveUseCase,
    private val updateUseCase: UpdateReserveUseCase,
    private val removeReserveUseCase: RemoveReserveUseCase,
    private val reserveModelDataMapper: ReserveModelDataMapper
) : ViewModel() {

    val reserveLiveData = MutableLiveData<Result>()
    val addReserveLiveData = MutableLiveData<Result>()
    val updateReserveLiveData = MutableLiveData<Result>()
    val removeReserveLiveData = MutableLiveData<Result>()

    private var reserves: ArrayList<ReserveModel>? = null
    private var response: Long? = null
    private var result: Result? = null
    private val disposables = CompositeDisposable()

    fun getReserves() {
        result = Result(null, null, State.LOADING_DATA, null)
        reserveLiveData.value = result

        val params = GetReservesUseCase.Params.forGetReserve()
        val d: Disposable? = reservesUseCase.execute(params)?.subscribe({ reservesList ->
            reserves = reserveModelDataMapper.transformReserveToReserveModels(reservesList)
            result = Result(null, reserves, State.DATA_LOADED, null)
            reserveLiveData.value = result
        }, { throwable ->
            reserves = null
            println("Login Error View Model $throwable.message")
            result = Result(null, reserves, State.LOAD_ERROR, throwable.message)
            reserveLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }
    }

    fun addReserve(reserve: ReserveModel) {
        result = Result(null, null, State.LOADING_DATA, null)
        addReserveLiveData.value = result

        val params = AddReserveUseCase.Params.forAddReserve(
            reserveModelDataMapper.transformReserveModelToReserve(reserve)
        )
        val d: Disposable? = addReserveUseCase.execute(params)?.subscribe({ response ->
            this.response = response
            result = Result(this.response, null, State.DATA_LOADED, null)
            addReserveLiveData.value = result
        }, { throwable ->
            response = null
            println("Login Error View Model $throwable.message")
            result = Result(response, null, State.LOAD_ERROR, throwable.message)
            addReserveLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }
    }

    fun updateReserve(reserve: ReserveModel) {
        result = Result(null, null, State.LOADING_DATA, null)
        updateReserveLiveData.value = result

        val params = UpdateReserveUseCase.Params.forUpdateReserve(
            reserveModelDataMapper.transformReserveModelToReserve(reserve)
        )
        val d: Disposable? = updateUseCase.execute(params)?.subscribe({ response ->
            this.response = response.toLong()
            result = Result(this.response, null, State.DATA_LOADED, null)
            addReserveLiveData.value = result
        }, { throwable ->
            response = null
            println("Login Error View Model $throwable.message")
            result = Result(response, null, State.LOAD_ERROR, throwable.message)
            addReserveLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }
    }

    fun removeReserve(reserve: ReserveModel) {
        var result: Result?
        result = Result(null, null, State.LOADING_DATA, null)

        removeReserveLiveData.postValue(result)
        val params = RemoveReserveUseCase.Params.forRemoveReserve(
            reserveModelDataMapper.transformReserveModelToReserve(reserve)
        )
        val d: Disposable? = removeReserveUseCase.execute(params)?.subscribe({ res ->
            result = Result(res.toLong(), null, State.DATA_LOADED, null)
            removeReserveLiveData.value = result
        }, { throwable ->
            result =
                Result(null, null, State.LOAD_ERROR, throwable.message)
            removeReserveLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    class Result(
        var response: Long?,
        var reserves: ArrayList<ReserveModel>?,
        var state: State,
        var error: String?,
    )

    enum class State {
        LOADING_DATA,
        DATA_LOADED,
        LOAD_ERROR,
    }

}
