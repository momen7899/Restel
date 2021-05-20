package com.momen.restel.room.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.momen.domain.interactor.AddRoomUseCase
import com.momen.domain.interactor.EditRoomUseCase
import com.momen.domain.interactor.GetRoomsUseCase
import com.momen.domain.interactor.RemoveRoomUseCase
import com.momen.restel.room.model.RoomModel
import com.momen.restel.room.model.RoomModelDataMapper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class RoomViewModel(
    private val addRoomUseCase: AddRoomUseCase,
    private val editRoomUseCase: EditRoomUseCase,
    private val getRoomsUseCase: GetRoomsUseCase,
    private val removeRoomUseCase: RemoveRoomUseCase,
    private val mapper: RoomModelDataMapper
) : ViewModel() {

    val addRoomLiveData = MutableLiveData<Result>()
    val editRoomLiveData = MutableLiveData<Result>()
    val getRoomsLiveData = MutableLiveData<Result>()
    val removeRoomLiveData = MutableLiveData<Result>()

    private val disposables = CompositeDisposable()

    fun addRoom(room: RoomModel) {
        var result: Result?
        result = Result(null, null, State.LOADING_DATA, null)
        addRoomLiveData.value = result

        println("AddRoom")

        val params = AddRoomUseCase.Params.forAddRoom(mapper.transformRoomModelToRoom(room))
        val d: Disposable? = addRoomUseCase.execute(params)?.subscribe({ res ->
            result = Result(res, null, State.DATA_LOADED, null)
            println(res)
            addRoomLiveData.value = result
        }, { throwable ->
            result = Result(null, null, State.LOAD_ERROR, throwable.message)
            addRoomLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }
    }

    fun editRoom(room: RoomModel) {
        var result: Result?
        result = Result(null, null, State.LOADING_DATA, null)
        editRoomLiveData.value = result

        println("editRoom")

        val params = EditRoomUseCase.Params.forEditRoom(mapper.transformRoomModelToRoom(room))
        val d: Disposable? = editRoomUseCase.execute(params)?.subscribe({ res ->
            result = Result(res.toLong(), null, State.DATA_LOADED, null)
            editRoomLiveData.value = result
            println(res)
        }, { throwable ->
            result = Result(null, null, State.LOAD_ERROR, throwable.message)
            editRoomLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    fun getRooms() {
        var result: Result?
        result = Result(null, null, State.LOADING_DATA, null)
        getRoomsLiveData.value = result

        val params = GetRoomsUseCase.Params.forGetRooms()
        val d: Disposable? = getRoomsUseCase.execute(params)?.subscribe({ res ->
            result = Result(
                null, mapper.transformRoomsToRoomModels(res), State.DATA_LOADED, null
            )
            getRoomsLiveData.value = result
        }, { throwable ->
            result = Result(null, null, State.LOAD_ERROR, throwable.message)
            getRoomsLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }

    }

    fun removeRoom(room: RoomModel) {
        var result: Result?
        result = Result(null, null, State.LOADING_DATA, null)
        removeRoomLiveData.postValue(result)

        val params = RemoveRoomUseCase.Params.forRemoveRoom(mapper.transformRoomModelToRoom(room))
        val d: Disposable? = removeRoomUseCase.execute(params)?.subscribe({ res ->
            result = Result(res.toLong(), null, State.DATA_LOADED, null)
            removeRoomLiveData.value = result
        }, { throwable ->
            result =
                Result(null, null, State.LOAD_ERROR, throwable.message)
            removeRoomLiveData.value = result
        }
        )
        d?.let { disposables.add(it) }
    }

    class Result(
        var response: Long?,
        var rooms: ArrayList<RoomModel>?,
        var state: State,
        var error: String?
    )

    enum class State {
        LOADING_DATA,
        DATA_LOADED,
        LOAD_ERROR
    }

}