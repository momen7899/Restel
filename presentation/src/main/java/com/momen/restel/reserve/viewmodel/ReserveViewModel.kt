package com.momen.restel.reserve.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.AddReserveUseCase
import com.momen.domain.interactor.UpdateReserveUseCase
import com.momen.restel.reserve.model.ReserveModel
import com.momen.restel.reserve.model.ReserveModelDataMapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class ReserveViewModel(
    private val addReserveUseCase: AddReserveUseCase,
    private val updateUseCase: UpdateReserveUseCase,
    private val reserveModelDataMapper: ReserveModelDataMapper
) : ViewModel() {

    val reserveLiveData = MutableLiveData<Result>()
    private var response: Long? = null
    private var result: Result? = null
    private val disposables = CompositeDisposable()

    fun addReserve(reserve: ReserveModel) {
        result = Result(null, State.LOADING_DATA, null)
        reserveLiveData.value = result

        val params = AddReserveUseCase.Params.forAddReserve(
            reserveModelDataMapper.transformReserveModelToReserve(reserve)
        )
        val d: Disposable? = addReserveUseCase.execute(params)?.subscribe({ response ->
            this.response = response
            result = Result(this.response, State.DATA_LOADED, null)
            reserveLiveData.value = result
        }, { throwable ->
            response = null
            println("Login Error View Model $throwable.message")
            result = Result(response, State.LOAD_ERROR, throwable.message)
            reserveLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }
    }

    class Result(
        var response: Long?,
        var state: State,
        var error: String?,
    )

    enum class State {
        LOADING_DATA,
        DATA_LOADED,
        LOAD_ERROR,
    }


}
