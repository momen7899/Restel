package com.momen.restel.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.GetReservesUseCase
import com.momen.restel.reserve.model.ReserveModel
import com.momen.restel.reserve.model.ReserveModelDataMapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MainReserveViewModel(
    private val reservesUseCase: GetReservesUseCase,
    private val reserveModelDataMapper: ReserveModelDataMapper
) : ViewModel() {
    val reserveLiveData = MutableLiveData<Result>()
    private var reserves: ArrayList<ReserveModel>? = null
    private var result: Result? = null
    private val disposables = CompositeDisposable()

    fun getReserves() {
        result = Result(null, State.LOADING_DATA, null)
        reserveLiveData.value = result

        val params = GetReservesUseCase.Params.forGetReserve()
        val d: Disposable = reservesUseCase.execute(params).subscribe({ reservesList ->
            reserves = reserveModelDataMapper.transformReserveToReserveModels(reservesList)
            result = Result(reserves, State.DATA_LOADED, null)
            reserveLiveData.value = result
        }, { throwable ->
            reserves = null
            println("Login Error View Model $throwable.message")
            result = Result(reserves, State.LOAD_ERROR, throwable.message)
            reserveLiveData.value = result
        }
        )
        disposables.add(d)
    }

    class Result(
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
