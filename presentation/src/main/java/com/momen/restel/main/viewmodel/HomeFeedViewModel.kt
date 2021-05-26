package com.momen.restel.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.HomeGetCustomersUseCase
import com.momen.domain.interactor.HomeGetRoomsUseCase
import com.momen.restel.main.model.HomeCustomerDataMapper
import com.momen.restel.main.model.HomeCustomerModel
import com.momen.restel.main.model.HomeRoomDataMapper
import com.momen.restel.main.model.HomeRoomModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class HomeFeedViewModel(
    private val customersUseCase: HomeGetCustomersUseCase,
    private val roomsUseCase: HomeGetRoomsUseCase,
    private val customerDataMapper: HomeCustomerDataMapper,
    private val roomDataMapper: HomeRoomDataMapper
) : ViewModel() {
    val getCustomerLiveData = MutableLiveData<Result>()
    val getRoomsLiveData = MutableLiveData<Result>()

    private val disposables = CompositeDisposable()

    fun getCustomers() {
        var result: Result?
        result = Result(null, null, State.LOADING_DATA, null)
        getCustomerLiveData.value = result

        val params = HomeGetCustomersUseCase.Params.forGetCustomers()
        val d: Disposable? = customersUseCase.execute(params)?.subscribe({ res ->
            result = Result(
                customerDataMapper.transformCustomerToCustomerModels(res),
                null,
                State.DATA_LOADED,
                null
            )
            println(result?.customers)
            getCustomerLiveData.value = result
        }, { throwable ->
            result =
                Result(
                    null,
                    null,
                    State.LOAD_ERROR,
                    throwable.message
                )
            getCustomerLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    fun getRooms() {
        var result: Result?
        result = Result(null, null, State.LOADING_DATA, null)
        getRoomsLiveData.value = result

        val params = HomeGetRoomsUseCase.Params.forGetRooms()
        val d: Disposable? = roomsUseCase.execute(params)?.subscribe({ res ->
            result = Result(
                null, roomDataMapper.transformRoomsToRoomModels(res), State.DATA_LOADED, null
            )
            getRoomsLiveData.value = result
        }, { throwable ->
            result =
                Result(null, null, State.LOAD_ERROR, throwable.message)
            getRoomsLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    class Result(
        var customers: ArrayList<HomeCustomerModel>?,
        val rooms: ArrayList<HomeRoomModel>?,
        var state: State,
        var error: String?
    )

    enum class State {
        LOADING_DATA,
        DATA_LOADED,
        LOAD_ERROR
    }
}
