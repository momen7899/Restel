package com.momen.restel.room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.momen.domain.interactor.AddRoomUseCase
import com.momen.domain.interactor.EditRoomUseCase
import com.momen.domain.interactor.GetRoomUseCase
import com.momen.domain.interactor.RemoveRoomUseCase
import com.momen.restel.room.model.RoomModelDataMapper
import javax.inject.Inject

class RoomViewModelFactory @Inject constructor(
    private val addRoomUseCase: AddRoomUseCase,
    private val editRoomUseCase: EditRoomUseCase,
    private val getRoomsUseCase: GetRoomUseCase,
    private val getRoomUseCase: GetRoomUseCase,
    private val removeRoomUseCase: RemoveRoomUseCase,
    private val roomModelDataMapper: RoomModelDataMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RoomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RoomViewModel(
                addRoomUseCase,
                editRoomUseCase,
                getRoomsUseCase,
                getRoomUseCase,
                removeRoomUseCase,
                roomModelDataMapper
            ) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}